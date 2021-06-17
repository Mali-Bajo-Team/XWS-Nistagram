package com.xws.users.service;

import com.xws.users.dto.VerificationRequestDTO;
import com.xws.users.users.model.VerificationRequest;

public interface IVerificationRequestService {
    VerificationRequest createVerificationRequest(VerificationRequestDTO verificationRequestDTO);
}
