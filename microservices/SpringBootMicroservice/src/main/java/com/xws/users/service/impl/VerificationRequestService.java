package com.xws.users.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.xws.users.dto.VerificationRequestDTO;
import com.xws.users.dto.adds.Influencer;
import com.xws.users.dto.adds.UserUpdateDTO;
import com.xws.users.repository.IRegularUserRepository;
import com.xws.users.repository.IUserCategoryRepository;
import com.xws.users.repository.IVerificationRequestRepository;
import com.xws.users.service.IAuthorityService;
import com.xws.users.service.IVerificationRequestService;
import com.xws.users.users.model.Authority;
import com.xws.users.users.model.VerificationRequest;
import com.xws.users.users.model.enums.RequestStatus;
import com.xws.users.users.model.roles.RegularUser;
import com.xws.users.util.security.exceptions.USConflictException;

@Service
public class VerificationRequestService implements IVerificationRequestService {

	private String addService = "http://add-service/";

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private IVerificationRequestRepository verificationRequestRepository;

	@Autowired
	private IUserCategoryRepository userCategoryRepository;

	@Autowired
	private IRegularUserRepository regularUserRepository;

	@Autowired
	private IAuthorityService authService;

	@Override
	public VerificationRequest createVerificationRequest(VerificationRequestDTO verificationRequestDTO) {
		VerificationRequest verificationRequest = new VerificationRequest();
		verificationRequest.setRealName(verificationRequestDTO.getRealName());
		verificationRequest.setRealSurname(verificationRequestDTO.getRealSurname());
		verificationRequest.setImageOfOfficialDocument(verificationRequestDTO.getImageOfOfficialDocument());
		verificationRequest.setCategory(userCategoryRepository.findByName(verificationRequestDTO.getCategory()));
		verificationRequest
				.setRequester(regularUserRepository.findByUsername(verificationRequestDTO.getRequesterUsername()));

		VerificationRequest verificationRequestAdded = verificationRequestRepository.save(verificationRequest);
		return verificationRequestAdded;
	}

	@Override
	@Transactional
	public VerificationRequest acceptVerificationRequest(Long verificationID) {
		VerificationRequest verificationRequestOld = verificationRequestRepository.findById(verificationID)
				.orElse(null);
		if (verificationRequestOld == null)
			return null; // Todo: Throw some exception
		verificationRequestOld.setRequestStatus(RequestStatus.ACCEPTED);
		VerificationRequest verificationRequestNew = verificationRequestRepository.save(verificationRequestOld);

		RegularUser regularUser = regularUserRepository.findById(verificationRequestNew.getRequester().getId())
				.orElse(null);
		if (regularUser == null)
			return null; // TODO: Throw some exception
		regularUser.setUserCategory(verificationRequestNew.getCategory());

		if (verificationRequestNew.getCategory().getName().equals("Influencer")) {
			List<Authority> auth = authService.findByName("ROLE_INFLUENCER");
			regularUser.setAuthorities(auth);
			
			String url = addService + "internal/influencer";
			HttpHeaders headers = new HttpHeaders();
			HttpEntity<Influencer> entity = new HttpEntity<Influencer>(new Influencer(regularUser), headers);
			ResponseEntity<Void> responseEntity = restTemplate.exchange(url, HttpMethod.POST, entity, Void.class);
			if (!responseEntity.getStatusCode().is2xxSuccessful()) {
				throw new USConflictException();
			}
		}

		regularUserRepository.save(regularUser);
		
		UserUpdateDTO dto = new UserUpdateDTO(regularUser.getUsername(), regularUser);
		
		String url = addService + "internal/update";
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<UserUpdateDTO> entity = new HttpEntity<UserUpdateDTO>(dto, headers);
		ResponseEntity<Void> responseEntity = restTemplate.exchange(url, HttpMethod.POST, entity, Void.class);
		if (!responseEntity.getStatusCode().is2xxSuccessful()) {
			throw new USConflictException();
		}

		return verificationRequestNew;
	}

	@Override
	public VerificationRequest rejectVerificationRequest(Long verificationID) {
		VerificationRequest verificationRequestOld = verificationRequestRepository.findById(verificationID)
				.orElse(null);
		if (verificationRequestOld == null)
			return null; // Todo: Throw some exception
		verificationRequestOld.setRequestStatus(RequestStatus.REJECTED);
		VerificationRequest verificationRequestNew = verificationRequestRepository.save(verificationRequestOld);
		return verificationRequestNew;
	}

	@Override
	public boolean isVerifiedUser(String username) {
		RegularUser regularUser = regularUserRepository.findByUsername(username);
		return regularUser.getUserCategory() != null;
	}

	@Override
	public List<VerificationRequest> findAllByRequestStatus(RequestStatus requestStatus) {
		return verificationRequestRepository.findByRequestStatus(requestStatus);
	}
}
