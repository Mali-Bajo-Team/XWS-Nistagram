package com.xws.users.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xws.users.dto.FollowRequestDTO;
import com.xws.users.dto.FollowerDTO;
import com.xws.users.dto.RegularUserMiniDTO;
import com.xws.users.service.IRelationshipService;
import com.xws.users.users.model.FollowRequest;
import com.xws.users.users.model.Relationship;
import com.xws.users.users.model.enums.RelationshipType;
import com.xws.users.users.model.roles.RegularUser;
import com.xws.users.users.model.roles.UserAccount;

@RestController
public class RelationshipController {

	@Autowired
	private IRelationshipService relationshipService;

	@GetMapping("following")
	@PreAuthorize("hasRole('REGULAR')")
	public ResponseEntity<List<RegularUserMiniDTO>> findFollowing(Authentication authentication) {
		UserAccount user = (UserAccount) authentication.getPrincipal();

		List<RegularUser> following = relationshipService.findFollowing(user.getId());
		List<RegularUserMiniDTO> retVal = new ArrayList<RegularUserMiniDTO>();
		for (RegularUser ruser : following) {
			retVal.add(new RegularUserMiniDTO(ruser));
		}

		return ResponseEntity.ok(retVal);
	}

	@GetMapping("followers")
	@PreAuthorize("hasRole('REGULAR')")
	public ResponseEntity<List<FollowerDTO>> findFollowers(Authentication authentication) {
		UserAccount user = (UserAccount) authentication.getPrincipal();
		
		return ResponseEntity.ok(relationshipService.findFollowers(user.getId()));
	}

	@PostMapping("follow/{username}")
	@PreAuthorize("hasRole('REGULAR')")
	public ResponseEntity<Void> follow(@PathVariable(required = true) String username, Authentication authentication) {
		UserAccount user = (UserAccount) authentication.getPrincipal();

		relationshipService.follow(user.getUsername(), username);

		return ResponseEntity.noContent().build();
	}

	@PostMapping("block/{username}")
	@PreAuthorize("hasRole('REGULAR')")
	public ResponseEntity<Void> block(@PathVariable(required = true) String username, Authentication authentication) {
		UserAccount user = (UserAccount) authentication.getPrincipal();

		//Bidirectional blocking
		relationshipService.block(user.getUsername(), username);

		return ResponseEntity.noContent().build();
	}

	@PostMapping("mute/{username}")
	@PreAuthorize("hasRole('REGULAR')")
	public ResponseEntity<Void> mute(@PathVariable(required = true) String username, Authentication authentication) {
		UserAccount user = (UserAccount) authentication.getPrincipal();

		relationshipService.mute(user.getUsername(), username);

		return ResponseEntity.noContent().build();
	}

	@PostMapping("unmute/{username}")
	@PreAuthorize("hasRole('REGULAR')")
	public ResponseEntity<Void> unmute(@PathVariable(required = true) String username, Authentication authentication) {
		UserAccount user = (UserAccount) authentication.getPrincipal();

		relationshipService.unmute(user.getUsername(), username);

		return ResponseEntity.noContent().build();
	}
	
	@PostMapping("unfollow/{username}")
	@PreAuthorize("hasRole('REGULAR')")
	public ResponseEntity<Void> unfollow(@PathVariable(required = true) String username, Authentication authentication) {
		UserAccount user = (UserAccount) authentication.getPrincipal();

		relationshipService.unfollow(user.getUsername(), username);

		return ResponseEntity.noContent().build();
	}

	@GetMapping("follow-requests")
	@PreAuthorize("hasRole('REGULAR')")
	public ResponseEntity<List<FollowRequestDTO>> findFollowRequests(Authentication authentication) {
		UserAccount user = (UserAccount) authentication.getPrincipal();

		List<FollowRequest> followRequests = relationshipService.findFollowRequests(user.getId());
		List<FollowRequestDTO> retVal = new ArrayList<FollowRequestDTO>();
		for (FollowRequest request : followRequests) {
			retVal.add(new FollowRequestDTO(request));
		}

		return ResponseEntity.ok(retVal);
	}

	@PostMapping("follow-requests/{id}/accept")
	@PreAuthorize("hasRole('REGULAR')")
	public ResponseEntity<Void> acceptFollowRequest(@PathVariable(required = true) Long id,
			Authentication authentication) {
		UserAccount user = (UserAccount) authentication.getPrincipal();

		relationshipService.acceptFollowRequest(id, user.getId());

		return ResponseEntity.noContent().build();
	}

	@PostMapping("follow-requests/{id}/reject")
	@PreAuthorize("hasRole('REGULAR')")
	public ResponseEntity<Void> rejectFollowRequest(@PathVariable(required = true) Long id,
			Authentication authentication) {
		UserAccount user = (UserAccount) authentication.getPrincipal();

		relationshipService.rejectFollowRequest(id, user.getId());

		return ResponseEntity.noContent().build();
	}
	
	@GetMapping("relationship/{username}")
	@PreAuthorize("hasRole('REGULAR')")
	public ResponseEntity<RelationshipType> getRelationship(@PathVariable(required = true) String username,
			Authentication authentication) {
		UserAccount user = (UserAccount) authentication.getPrincipal();

		Relationship relationship = relationshipService.findRelationship(user.getUsername(), username);
		if (relationship == null)
			return ResponseEntity.ok(RelationshipType.NONE);
		else 
			return ResponseEntity.ok(relationship.getRelationshipType());
	}

	@GetMapping("ismuted/{username}")
	@PreAuthorize("hasRole('REGULAR')")
	public ResponseEntity<Boolean> isMuted(@PathVariable(required = true) String username,
															Authentication authentication) {
		UserAccount user = (UserAccount) authentication.getPrincipal();

		Relationship relationship = relationshipService.findRelationship(user.getUsername(), username);
		if (relationship == null)
			return ResponseEntity.ok(null);
		else
			return ResponseEntity.ok(relationship.isMuted());
	}
	
	@GetMapping("close-friends")
	@PreAuthorize("hasRole('REGULAR')")
	public ResponseEntity<List<RegularUserMiniDTO>> findCloseFriends(Authentication authentication) {
		UserAccount user = (UserAccount) authentication.getPrincipal();

		List<RegularUser> closeFriends = relationshipService.findCloseFriends(user.getId());
		List<RegularUserMiniDTO> retVal = new ArrayList<RegularUserMiniDTO>();
		for (RegularUser ruser : closeFriends) {
			retVal.add(new RegularUserMiniDTO(ruser));
		}

		return ResponseEntity.ok(retVal);
	}
	
	@PostMapping("close-friends/add/{username}")
	@PreAuthorize("hasRole('REGULAR')")
	public ResponseEntity<Void> addToClose(@PathVariable(required = true) String username, Authentication authentication) {
		UserAccount user = (UserAccount) authentication.getPrincipal();

		relationshipService.addToCloseFriends(user.getUsername(), username);

		return ResponseEntity.noContent().build();
	}
	
	@PostMapping("close-friends/remove/{username}")
	@PreAuthorize("hasRole('REGULAR')")
	public ResponseEntity<Void> removeFromClose(@PathVariable(required = true) String username, Authentication authentication) {
		UserAccount user = (UserAccount) authentication.getPrincipal();

		relationshipService.removeFromCloseFriends(user.getUsername(), username);

		return ResponseEntity.noContent().build();
	}
	
}
