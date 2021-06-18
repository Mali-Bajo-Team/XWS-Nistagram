package com.xws.users.service.impl;

import com.xws.users.dto.VerificationRequestDTO;
import com.xws.users.repository.IRegularUserRepository;
import com.xws.users.repository.IUserCategoryRepository;
import com.xws.users.repository.IVerificationRequestRepository;
import com.xws.users.service.IVerificationRequestService;
import com.xws.users.users.model.VerificationRequest;
import com.xws.users.users.model.enums.RequestStatus;
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

    @Override
    public VerificationRequest createVerificationRequest(VerificationRequestDTO verificationRequestDTO) {
        VerificationRequest verificationRequest = new VerificationRequest();
        verificationRequest.setRealName(verificationRequestDTO.getRealName());
        verificationRequest.setRealSurname(verificationRequestDTO.getRealSurname());
        verificationRequest.setImageOfOfficialDocument(verificationRequestDTO.getImageOfOfficialDocument());
        verificationRequest.setCategory(userCategoryRepository.findByName(verificationRequestDTO.getCategory()));
        verificationRequest.setRequester(regularUserRepository.findByUsername(verificationRequestDTO.getRequesterUsername()));

        VerificationRequest verificationRequestAdded = verificationRequestRepository.save(verificationRequest);
        return verificationRequestAdded;
    }

    @Override
    public List<VerificationRequest> findAll() {
        return verificationRequestRepository.findAll();
    }

    @Override
    public List<VerificationRequest> findAllByRequestStatus(RequestStatus requestStatus) {
        return verificationRequestRepository.findByRequestStatus(requestStatus);
    }
}
