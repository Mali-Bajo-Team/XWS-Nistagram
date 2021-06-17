package com.xws.users.repository;

import com.xws.users.users.model.VerificationRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IVerificationRequest extends JpaRepository<VerificationRequest, Long> {
}
