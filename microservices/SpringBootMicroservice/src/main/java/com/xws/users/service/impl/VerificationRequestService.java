package com.xws.users.service.impl;

import com.xws.users.dto.VerificationRequestDTO;
import com.xws.users.repository.IRegularUserRepository;
import com.xws.users.repository.IUserCategoryRepository;
import com.xws.users.repository.IVerificationRequestRepository;
import com.xws.users.service.IAuthorityService;
import com.xws.users.service.IVerificationRequestService;
import com.xws.users.users.model.Authority;
import com.xws.users.users.model.VerificationRequest;
import com.xws.users.users.model.enums.RequestStatus;
import com.xws.users.users.model.roles.RegularUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VerificationRequestService implements IVerificationRequestService {

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
		}

		regularUserRepository.save(regularUser);

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
