package com.xws.adds.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xws.adds.model.CollaborationRequest;
import com.xws.adds.security.CustomUserDetails;
import com.xws.adds.service.ICollaborationService;

@RestController
public class CollaborationController {

	@Autowired
	private ICollaborationService collaborationService;

	@PreAuthorize("hasRole('AGENT')")
	@GetMapping("influencers/collaborating")
	public ResponseEntity<List<String>> getCollaborators(Authentication authentication) {
		CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();

		return ResponseEntity.ok(collaborationService.getCollaboratingInfluencers(user.getUsername()));
	}

	@PreAuthorize("hasRole('AGENT')")
	@GetMapping("influencers/non-collaborating")
	public ResponseEntity<List<String>> getInfluencers(Authentication authentication) {
		CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();

		return ResponseEntity.ok(collaborationService.getAvailableInfluencers(user.getUsername()));
	}

	@PreAuthorize("hasRole('AGENT')")
	@PostMapping("influencers/{username}/request")
	public ResponseEntity<Void> requestCollaboration(Authentication authentication,
			@PathVariable(required = true) String username) {
		CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();

		collaborationService.requestCollaboration(user.getUsername(), username);

		return ResponseEntity.noContent().build();
	}

	@PreAuthorize("hasRole('INFLUENCER')")
	@GetMapping("requests")
	public ResponseEntity<List<CollaborationRequest>> getRequests(Authentication authentication) {
		CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();

		return ResponseEntity.ok(collaborationService.getCollaborationRequests(user.getUsername()));
	}
	
	@PreAuthorize("hasRole('INFLUENCER')")
	@PostMapping("requests/{id}/accept")
	public ResponseEntity<Void> acceptRequest(Authentication authentication,
			@PathVariable(required = true) Long id) {
		CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();

		collaborationService.acceptCollaborationRequest(user.getUsername(), id);

		return ResponseEntity.noContent().build();
	}
	
	@PreAuthorize("hasRole('INFLUENCER')")
	@PostMapping("requests/{id}/reject")
	public ResponseEntity<Void> rejectRequest(Authentication authentication,
			@PathVariable(required = true) Long id) {
		CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();

		collaborationService.rejectCollaborationRequest(user.getUsername(), id);

		return ResponseEntity.noContent().build();
	}

}
