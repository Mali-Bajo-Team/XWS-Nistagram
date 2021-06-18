package com.xws.users.service;

import com.xws.users.dto.VerificationRequestDTO;
import com.xws.users.users.model.VerificationRequest;

import java.util.List;

public interface IVerificationRequestService {
    VerificationRequest createVerificationRequest(VerificationRequestDTO verificationRequestDTO);
    List<VerificationRequest> findAll();
}
