package com.xws.users.service;

import java.util.List;

import com.xws.users.dto.FollowerDTO;
import com.xws.users.users.model.FollowRequest;
import com.xws.users.users.model.Relationship;
import com.xws.users.users.model.roles.RegularUser;

public interface IRelationshipService {
	
	List<FollowerDTO> findFollowers(Long id);

	List<RegularUser> findFollowing(Long id);
	
	void follow(String fromUsername, String towardsUsername);
	
	void unfollow(String fromUsername, String towardsUsername);
	
	List<FollowRequest> findFollowRequests(Long id);
	
	void acceptFollowRequest(Long id, Long userId);
	
	void rejectFollowRequest(Long id, Long userId);
	
	Relationship findRelationship(String fromUsername, String towardsUsername);
	
	List<RegularUser> findCloseFriends(Long id);
	
	void addToCloseFriends(String username, String closeFriendUsername);
	
	void removeFromCloseFriends(String username, String closeFriendUsername);
	
}