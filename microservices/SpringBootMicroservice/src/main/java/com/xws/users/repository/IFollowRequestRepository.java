package com.xws.users.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.xws.users.users.model.FollowRequest;

public interface IFollowRequestRepository extends JpaRepository<FollowRequest, Long> {

	List<FollowRequest> findByAccountToFollowId(Long accountToFollowId);
	
}
