package com.xws.users.repository;

import com.xws.users.users.model.VerificationRequest;
import com.xws.users.users.model.enums.RequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IVerificationRequestRepository extends JpaRepository<VerificationRequest, Long> {
    List<VerificationRequest> findByRequestStatus(RequestStatus requestStatus);
}
