package com.xws.users.repository;

import com.xws.users.users.model.RegistrationRequest;
import com.xws.users.users.model.enums.RequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IRegistrationRequestRepository extends JpaRepository<RegistrationRequest,Long> {
    List<RegistrationRequest> findByRequestStatus(RequestStatus requestStatus);
}
