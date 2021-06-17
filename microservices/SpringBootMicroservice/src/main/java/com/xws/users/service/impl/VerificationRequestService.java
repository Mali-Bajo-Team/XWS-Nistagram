package com.xws.users.service.impl;

import com.xws.users.repository.IUserRepository;
import com.xws.users.repository.IVerificationRequestRepository;
import com.xws.users.service.IVerificationRequestService;
import com.xws.users.users.model.VerificationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VerificationRequestService implements IVerificationRequestService {

    @Autowired
    private IVerificationRequestRepository verificationRequestRepository;

    @Override
    public VerificationRequest createVerificationRequest() {
        return null;
    }
}
