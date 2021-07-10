package com.xws.adds.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.xws.adds.dto.MultipleCampaignDTO;
import com.xws.adds.dto.MultipleCampaignUpdateDTO;
import com.xws.adds.dto.OneTimeCampaignDTO;
import com.xws.adds.dto.post.Post;
import com.xws.adds.dto.post.RegularPost;
import com.xws.adds.dto.post.Story;
import com.xws.adds.model.AcceptedCampaign;
import com.xws.adds.model.AddCampaign;
import com.xws.adds.model.Agent;
import com.xws.adds.model.CampaignType;
import com.xws.adds.model.Collaboration;
import com.xws.adds.model.Influencer;
import com.xws.adds.repository.IAcceptedCampaignRepository;
import com.xws.adds.repository.IAddCampaignRepository;
import com.xws.adds.repository.IAgentRepository;
import com.xws.adds.repository.IInfluencerRepository;
import com.xws.adds.service.ICampaignService;
import com.xws.adds.util.exceptions.USAuthenticationException;
import com.xws.adds.util.exceptions.USAuthorizationException;
import com.xws.adds.util.exceptions.USConflictException;

@Service
public class CampaignService implements ICampaignService {

	private String contentService = "http://content-service:8000";

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private IAgentRepository agentRepository;

	@Autowired
	private IAddCampaignRepository addCampaignRepository;

	@Autowired
	private IInfluencerRepository influencerRepository;
	
	@Autowired
	private IAcceptedCampaignRepository acceptedRepository;

	@Override
	public List<AddCampaign> getCampaigns(String username) {
		Agent agent = agentRepository.findByUsername(username);
		if (agent == null)
			throw new USAuthenticationException();

		return addCampaignRepository.findByAgent(agent);
	}
	
	public List<AddCampaign> getPendingCampaigns(String influencerUsername) {
		Influencer influencer = influencerRepository.findByUsername(influencerUsername);
		if (influencer == null)
			throw new USAuthenticationException();
		
		return influencer.getPendingCampaigns();
	}
	
	@Transactional
	public void rejectCampaign(String influencerUsername, Long campaignId) {
		Influencer influencer = influencerRepository.findByUsername(influencerUsername);
		if (influencer == null)
			throw new USAuthenticationException();
		
		AddCampaign campaign = addCampaignRepository.findById(campaignId).orElse(null);
		if (campaign == null)
			throw new USConflictException();
		
		influencer.getPendingCampaigns().remove(campaign);
		influencerRepository.save(influencer);
	}
	
	@Transactional
	public void acceptCampaign(String influencerUsername, Long campaignId) {
		Influencer influencer = influencerRepository.findByUsername(influencerUsername);
		if (influencer == null)
			throw new USAuthenticationException();
		
		AddCampaign campaign = addCampaignRepository.findById(campaignId).orElse(null);
		if (campaign == null)
			throw new USConflictException();
		
		influencer.getPendingCampaigns().remove(campaign);
		influencerRepository.save(influencer);
		
		String postId = null;
		if (campaign.getType().equals(CampaignType.POST))
			postId = transferPostToInfluencer(campaign.getPostId(), influencerUsername);
		else
			postId = transferStoryToInfluencer(campaign.getPostId(), influencerUsername);
		
		AcceptedCampaign accepted = new AcceptedCampaign();
		accepted.setPostId(postId);
		accepted.setInfluencer(influencer);
		accepted.setCampaign(campaign);
		
		acceptedRepository.save(accepted);
	}

	@Override
	@Transactional
	public void deleteCampaign(Long id, String username) {
		Agent agent = agentRepository.findByUsername(username);
		if (agent == null)
			throw new USAuthenticationException();

		AddCampaign campaign = addCampaignRepository.findById(id).orElse(null);
		if (campaign == null)
			throw new USConflictException();

		if (!campaign.getAgent().getUsername().equals(username))
			throw new USAuthorizationException();

		campaign.setDeleted(true);

		addCampaignRepository.save(campaign);
	}

	@Override
	@Transactional
	public AddCampaign updateCampaign(Long id, String username, MultipleCampaignUpdateDTO update) {
		Agent agent = agentRepository.findByUsername(username);
		if (agent == null)
			throw new USAuthenticationException();

		AddCampaign campaign = addCampaignRepository.findById(id).orElse(null);
		if (campaign == null)
			throw new USConflictException();

		if (!campaign.getAgent().getUsername().equals(username))
			throw new USAuthorizationException();

		Date timeLimit = (new LocalDateTime(new Date())).plusHours(24).toDate();
		Date start = timeLimit;
		campaign.setEndDate(update.getEndDate());
		if (campaign.getStartDate().after(timeLimit)) {
			campaign.setStartDate(update.getStartDate());
			start = campaign.getStartDate();
		}

		List<Date> exposureTimes = new ArrayList<Date>();
		for (Date date : campaign.getExposureTimes()) {
			if (date.before(timeLimit))
				exposureTimes.add(date);
		}

		for (Date date : getExposureTimesForMultiple(start, campaign.getEndDate(), campaign.getTimesPerDay())) {
			exposureTimes.add(date);
		}

		campaign.setExposureTimes(exposureTimes);

		campaign.setLastEdited(new Date());

		return addCampaignRepository.save(campaign);
	}

	@Override
	@Transactional
	public AddCampaign createOneTimeCampaign(OneTimeCampaignDTO campaign, String username) {
		Agent agent = agentRepository.findByUsername(username);
		if (agent == null)
			throw new USAuthenticationException();

		AddCampaign addCampaign = new AddCampaign();

		String postId = null;
		if (campaign.getIsStory()) {
			addCampaign.setType(CampaignType.STORY);
			postId = createStory(campaign.getPost(), username);
		} else {
			addCampaign.setType(CampaignType.POST);
			postId = createPost(campaign.getPost(), username);
		}

		addCampaign.setInterestGroup(campaign.getInterestGroup());
		addCampaign.setPostId(postId);
		addCampaign.setOneTime(true);
		addCampaign.setAgent(agent);
		addCampaign.setExposureTimes(new ArrayList<Date>());
		addCampaign.getExposureTimes().add(campaign.getExposureTime());
		addCampaign.setDeleted(false);

		AddCampaign createdCampaign = addCampaignRepository.save(addCampaign);

		offerToInfluencers(createdCampaign, agent);

		return createdCampaign;
	}

	@Override
	@Transactional
	public AddCampaign createMultipleCampaign(MultipleCampaignDTO campaign, String username) {
		Agent agent = agentRepository.findByUsername(username);
		if (agent == null)
			throw new USAuthenticationException();

		AddCampaign addCampaign = new AddCampaign();

		String postId = null;
		if (campaign.getIsStory()) {
			addCampaign.setType(CampaignType.STORY);
			postId = createStory(campaign.getPost(), username);
		} else {
			addCampaign.setType(CampaignType.POST);
			postId = createPost(campaign.getPost(), username);
		}

		addCampaign.setInterestGroup(campaign.getInterestGroup());
		addCampaign.setPostId(postId);
		addCampaign.setOneTime(true);
		addCampaign.setAgent(agent);
		addCampaign.setStartDate(campaign.getStartDate());
		addCampaign.setEndDate(campaign.getEndDate());
		addCampaign.setTimesPerDay(campaign.getTimesPerDay());
		addCampaign.setExposureTimes(getExposureTimesForMultiple(addCampaign.getStartDate(), addCampaign.getEndDate(),
				addCampaign.getTimesPerDay()));
		addCampaign.setLastEdited(new Date());
		addCampaign.setDeleted(false);

		AddCampaign createdCampaign = addCampaignRepository.save(addCampaign);

		offerToInfluencers(createdCampaign, agent);

		return createdCampaign;
	}

	private String createStory(Post post, String username) {
		post.setIsAdd(true);

		Story story = new Story();
		story.setPost(post);
		story.setIsForClose(false);

		String url = contentService + "story/";
		HttpHeaders headers = new HttpHeaders();
		headers.add("user-username", username);
		HttpEntity<Story> entity = new HttpEntity<Story>(story, headers);
		ResponseEntity<Story> responseEntity = restTemplate.exchange(url, HttpMethod.POST, entity, Story.class);
		if (!responseEntity.getStatusCode().is2xxSuccessful()) {
			throw new USConflictException();
		}

		return entity.getBody().getId();
	}

	private String createPost(Post post, String username) {
		post.setIsAdd(true);

		RegularPost regPost = new RegularPost();
		regPost.setPost(post);

		String url = contentService + "post/";
		HttpHeaders headers = new HttpHeaders();
		headers.add("user-username", username);
		HttpEntity<RegularPost> entity = new HttpEntity<RegularPost>(regPost, headers);
		ResponseEntity<RegularPost> responseEntity = restTemplate.exchange(url, HttpMethod.POST, entity,
				RegularPost.class);
		if (!responseEntity.getStatusCode().is2xxSuccessful()) {
			throw new USConflictException();
		}

		return entity.getBody().getId();
	}

	private List<Date> getExposureTimesForMultiple(Date startD, Date endD, int timesPerDay) {
		LocalDateTime start = new LocalDateTime(startD).withTime(0, 0, 0, 0);
		LocalDateTime end = new LocalDateTime(endD).withTime(0, 0, 0, 0);
		int interval = 24 * 60 / timesPerDay;
		if (interval < 1)
			interval = 1;

		List<Date> times = new ArrayList<Date>();

		for (LocalDateTime date = start; date.isBefore(end); date.plusMinutes(interval))
			times.add(date.toDate());

		return times;
	}

	private void offerToInfluencers(AddCampaign campaign, Agent agent) {
		for (Collaboration collaboration : agent.getInfluencers()) {
			Influencer influencer = collaboration.getInfluencer();
			influencer.getPendingCampaigns().add(campaign);
			influencerRepository.save(influencer);
		}
	}

	private String transferPostToInfluencer(String postId, String influencerUsername) {
		String url = contentService + "post/" + postId;
		HttpHeaders headers = new HttpHeaders();
		headers.add("user-username", influencerUsername);
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);
		ResponseEntity<RegularPost> responseEntity = restTemplate.exchange(url, HttpMethod.GET, entity,
				RegularPost.class);
		if (!responseEntity.getStatusCode().is2xxSuccessful()) {
			throw new USConflictException();
		}
		RegularPost post = responseEntity.getBody();

		return createPost(post.getPost(), influencerUsername);
	}

	private String transferStoryToInfluencer(String postId, String influencerUsername) {
		String url = contentService + "story/" + postId;
		HttpHeaders headers = new HttpHeaders();
		headers.add("user-username", influencerUsername);
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);
		ResponseEntity<Story> responseEntity = restTemplate.exchange(url, HttpMethod.GET, entity, Story.class);
		if (!responseEntity.getStatusCode().is2xxSuccessful()) {
			throw new USConflictException();
		}
		Story post = responseEntity.getBody();

		return createPost(post.getPost(), influencerUsername);
	}

}
