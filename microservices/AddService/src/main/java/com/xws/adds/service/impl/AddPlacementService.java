package com.xws.adds.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xws.adds.model.AddCampaign;
import com.xws.adds.model.AddConsumer;
import com.xws.adds.model.InterestGroupDescription;
import com.xws.adds.repository.IAddCampaignRepository;
import com.xws.adds.repository.IAddConsumerRepository;
import com.xws.adds.service.IAddPlacementService;
import com.xws.adds.util.exceptions.USAuthenticationException;

@Service
public class AddPlacementService implements IAddPlacementService {

	@Autowired
	private IAddCampaignRepository campaignRepository;

	@Autowired
	private IAddConsumerRepository consumerRepository;

	@Override
	public AddCampaign getAdd(String username) {
		AddConsumer consumer = consumerRepository.findByUsername(username);
		if (consumer == null)
			throw new USAuthenticationException();

		Date latest = (new LocalDateTime(new Date())).plusMinutes(5).toDate();
		Date earliest = (new LocalDateTime(new Date())).minusMinutes(5).toDate();

		List<AddCampaign> correctTime = new ArrayList<AddCampaign>();
		for (AddCampaign addCampaign : campaignRepository.findAll()) {
			for (Date date : addCampaign.getExposureTimes()) {
				if (date.after(earliest) && date.before(latest)) {
					correctTime.add(addCampaign);
					break;
				}
			}
		}
		
		List<AddCampaign> options = new ArrayList<AddCampaign>();
		for (AddCampaign addCampaign : correctTime) {
			if (matchesInterestGroup(consumer, addCampaign.getInterestGroup()))
				options.add(addCampaign);
		}
		
		int length = options.size();
		
		if (length == 0)
			return null;
		
		Random rand = new Random(new Date().getTime());
		int chosen = rand.nextInt(length);

		return options.get(chosen);
	}

	private Boolean matchesInterestGroup(AddConsumer consumer, InterestGroupDescription group) {
		if (group.getGender() != null && consumer.getGender() != null && consumer.getGender().equals(group.getGender()))
			return true;

		if (group.getUserCategory() != null && consumer.getUserCategory() != null
				&& consumer.getUserCategory().equals(group.getUserCategory()))
			return true;
		
		if (consumer.getDateOfBirth() != null) {
			int year = (new LocalDate(new Date())).getYear();
			int birthYear = (new LocalDate(consumer.getDateOfBirth())).getYear();
			
			int age = year - birthYear;
			
			if (group.getMinimumAge() < age && group.getMaximumAge() > age)
				return true;
		}
		
		return false;
	}

}
