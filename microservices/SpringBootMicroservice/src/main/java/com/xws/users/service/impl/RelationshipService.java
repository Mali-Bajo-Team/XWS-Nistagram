package com.xws.users.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xws.users.dto.FollowerDTO;
import com.xws.users.repository.IFollowRequestRepository;
import com.xws.users.repository.IRegularUserRepository;
import com.xws.users.repository.IRelationshipRepository;
import com.xws.users.service.IRelationshipService;
import com.xws.users.users.model.FollowRequest;
import com.xws.users.users.model.Relationship;
import com.xws.users.users.model.enums.RelationshipType;
import com.xws.users.users.model.roles.RegularUser;
import com.xws.users.util.security.exceptions.USAuthenticationException;
import com.xws.users.util.security.exceptions.USAuthorizationException;
import com.xws.users.util.security.exceptions.USConflictException;

@Service
public class RelationshipService implements IRelationshipService {

	@Autowired
	private IRegularUserRepository userRepository;

	@Autowired
	private IRelationshipRepository relationshipRepository;

	@Autowired
	private IFollowRequestRepository followRequestRepository;

	@Override
	public List<FollowerDTO> findFollowers(Long id) {
		RegularUser user = userRepository.findById(id).orElse(null);
		if (user == null)
			throw new USAuthenticationException("User not found.");

		List<FollowerDTO> followers = new ArrayList<FollowerDTO>();

		for (Relationship relationship : user.getInRelationships()) {
			if (relationship.getRelationshipType().equals(RelationshipType.FOLLOWED)) {
				followers.add(new FollowerDTO(relationship.getFrom(), false));
			} else if (relationship.getRelationshipType().equals(RelationshipType.CLOSE_FRIEND)) {
				followers.add(new FollowerDTO(relationship.getFrom(), true));
			}
		}

		return followers;
	}

	@Override
	public List<RegularUser> findFollowing(Long id) {
		RegularUser user = userRepository.findById(id).orElse(null);
		if (user == null)
			throw new USAuthenticationException("User not found.");

		List<RegularUser> following = new ArrayList<RegularUser>();

		for (Relationship relationship : user.getOutRelationships()) {
			if (relationship.getRelationshipType().equals(RelationshipType.FOLLOWED)
					|| relationship.getRelationshipType().equals(RelationshipType.CLOSE_FRIEND)) {
				following.add(relationship.getTowards());
			}
		}

		return following;
	}

	@Override
	public void follow(String fromUsername, String towardsUsername) {
		RegularUser from = userRepository.findByUsername(fromUsername);
		if (from == null)
			throw new USAuthenticationException("User not found.");
		RegularUser towards = userRepository.findByUsername(towardsUsername);
		if (towards == null)
			throw new USConflictException("User with the requested username does not exist.");

		Relationship existingRelationship = relationshipRepository.findByFromAndTowards(from, towards);
		Relationship oppositeRelationship = relationshipRepository.findByFromAndTowards(towards, from);

		if (existingRelationship != null
				&& existingRelationship.getRelationshipType().equals(RelationshipType.BLOCKED)) {
			throw new USConflictException("You cannot follow the requested user because you have blocked them.");
		}
		if (existingRelationship != null
				&& (existingRelationship.getRelationshipType().equals(RelationshipType.FOLLOWED)
						|| existingRelationship.getRelationshipType().equals(RelationshipType.CLOSE_FRIEND))) {
			throw new USConflictException("You already follow the requested user.");
		}
		if (oppositeRelationship != null
				&& oppositeRelationship.getRelationshipType().equals(RelationshipType.BLOCKED)) {
			throw new USConflictException("You cannot follow the requested user because they have blocked you.");
		}

		if (towards.getPrivacySettings().isPrivate()) {
			createFollowRequest(from, towards);
			return;
		}

		if (existingRelationship == null) {
			existingRelationship = new Relationship();
			existingRelationship.setFrom(from);
			existingRelationship.setTowards(towards);
		}

		existingRelationship.setRelationshipType(RelationshipType.FOLLOWED);

		relationshipRepository.save(existingRelationship);
	}

	@Override
	public void unfollow(String fromUsername, String towardsUsername) {
		RegularUser from = userRepository.findByUsername(fromUsername);
		if (from == null)
			throw new USAuthenticationException("User not found.");
		RegularUser towards = userRepository.findByUsername(towardsUsername);
		if (towards == null)
			throw new USConflictException("User with the requested username does not exist.");

		Relationship existingRelationship = relationshipRepository.findByFromAndTowards(from, towards);

		if (!(existingRelationship != null
				&& (existingRelationship.getRelationshipType().equals(RelationshipType.FOLLOWED)
						|| existingRelationship.getRelationshipType().equals(RelationshipType.CLOSE_FRIEND)))) {
			throw new USConflictException("You don't follow the requested user.");
		}

		relationshipRepository.delete(existingRelationship);
	}

	private void createFollowRequest(RegularUser from, RegularUser towards) {
		FollowRequest request = new FollowRequest();
		request.setRequster(from);
		request.setAccountToFollow(towards);

		followRequestRepository.save(request);
	}

	@Override
	public void acceptFollowRequest(Long id, Long userId) {
		FollowRequest request = followRequestRepository.findById(id).orElse(null);
		if (request == null)
			throw new USConflictException("Follow request not found.");

		if (!request.getAccountToFollow().getId().equals(userId))
			throw new USAuthorizationException("Not authorized.");

		Relationship existingRelationship = relationshipRepository.findByFromAndTowards(request.getRequster(),
				request.getAccountToFollow());

		if (existingRelationship == null) {
			existingRelationship = new Relationship();
			existingRelationship.setFrom(request.getRequster());
			existingRelationship.setTowards(request.getAccountToFollow());
		}

		existingRelationship.setRelationshipType(RelationshipType.FOLLOWED);

		relationshipRepository.save(existingRelationship);
		followRequestRepository.delete(request);
	}

	@Override
	public void rejectFollowRequest(Long id, Long userId) {
		FollowRequest request = followRequestRepository.findById(id).orElse(null);
		if (request == null)
			throw new USConflictException("Follow request not found.");

		if (!request.getAccountToFollow().getId().equals(userId))
			throw new USAuthorizationException("Not authorized.");

		followRequestRepository.delete(request);
	}

	@Override
	public List<FollowRequest> findFollowRequests(Long id) {
		return followRequestRepository.findByAccountToFollowId(id);
	}

	@Override
	public Relationship findRelationship(String fromUsername, String towardsUsername) {
		RegularUser from = userRepository.findByUsername(fromUsername);
		if (from == null)
			throw new USAuthenticationException("User not found.");
		RegularUser towards = userRepository.findByUsername(towardsUsername);
		if (towards == null)
			throw new USConflictException("User with the requested username does not exist.");

		Relationship existingRelationship = relationshipRepository.findByFromAndTowards(from, towards);

		return existingRelationship;
	}

	@Override
	public List<RegularUser> findCloseFriends(Long id) {
		RegularUser user = userRepository.findById(id).orElse(null);
		if (user == null)
			throw new USAuthenticationException("User not found.");

		List<RegularUser> closeFriends = new ArrayList<RegularUser>();

		for (Relationship relationship : user.getInRelationships()) {
			if (relationship.getRelationshipType().equals(RelationshipType.CLOSE_FRIEND)) {
				closeFriends.add(relationship.getFrom());
			}
		}

		return closeFriends;
	}

	@Override
	public void addToCloseFriends(String username, String closeFriendUsername) {
		Relationship relationship = findRelationship(closeFriendUsername, username);

		if (!(relationship != null && (relationship.getRelationshipType().equals(RelationshipType.FOLLOWED)
				|| relationship.getRelationshipType().equals(RelationshipType.CLOSE_FRIEND))))
			throw new USConflictException("The requested user doesn't follow.");

		relationship.setRelationshipType(RelationshipType.CLOSE_FRIEND);
		relationshipRepository.save(relationship);
	}

	@Override
	public void removeFromCloseFriends(String username, String closeFriendUsername) {
		Relationship relationship = findRelationship(closeFriendUsername, username);

		if (!(relationship != null && relationship.getRelationshipType().equals(RelationshipType.CLOSE_FRIEND)))
			throw new USConflictException("The requested user isn't a close friend.");

		relationship.setRelationshipType(RelationshipType.FOLLOWED);
		relationshipRepository.save(relationship);
	}

	@Override
	public void block(String fromUsername, String towardsUsername) {
		RegularUser from = userRepository.findByUsername(fromUsername);
		if (from == null)
			throw new USAuthenticationException("User not found.");
		RegularUser towards = userRepository.findByUsername(towardsUsername);
		if (towards == null)
			throw new USConflictException("User with the requested username does not exist.");

		Relationship existingRelationship = relationshipRepository.findByFromAndTowards(from, towards);
		Relationship oppositeRelationship = relationshipRepository.findByFromAndTowards(towards, from);

		if (existingRelationship != null
				&& existingRelationship.getRelationshipType().equals(RelationshipType.BLOCKED)) {
			throw new USConflictException("You cannot block the requested user because you have blocked them.");
		}
		if (oppositeRelationship != null
				&& oppositeRelationship.getRelationshipType().equals(RelationshipType.BLOCKED)) {
			throw new USConflictException("You cannot block the requested user because they have blocked you.");
		}

		existingRelationship = new Relationship();
		oppositeRelationship = new Relationship();
		existingRelationship.setFrom(from);
		existingRelationship.setTowards(towards);
		oppositeRelationship.setFrom(towards);
		oppositeRelationship.setTowards(from);

		existingRelationship.setRelationshipType(RelationshipType.BLOCKED);
		oppositeRelationship.setRelationshipType(RelationshipType.BLOCKED);

		relationshipRepository.save(existingRelationship);
		relationshipRepository.save(oppositeRelationship);
	}

}
