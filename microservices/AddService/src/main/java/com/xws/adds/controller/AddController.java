package com.xws.adds.controller;

import java.util.List;

import javax.persistence.metamodel.PluralAttribute.CollectionType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xws.adds.model.AddCampaign;
import com.xws.adds.model.CampaignType;
import com.xws.adds.security.CustomUserDetails;
import com.xws.adds.service.IAddPlacementService;

@RestController("add")
public class AddController {

	@Autowired
	private IAddPlacementService service;

	@PreAuthorize("hasAnyRole('AGENT', 'REGULAR', 'INFLUENCER')")
	@GetMapping("/story")
	public ResponseEntity<AddCampaign> getStoryAdd(Authentication authentication) {
		CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();

		AddCampaign campaign = service.getAdd(user.getUsername(), CampaignType.STORY);

		if (campaign == null)
			return ResponseEntity.noContent().build();
		else
			return ResponseEntity.ok(campaign);
	}
	
	@PreAuthorize("hasAnyRole('AGENT', 'REGULAR', 'INFLUENCER')")
	@GetMapping("/post")
	public ResponseEntity<AddCampaign> getPostAdd(Authentication authentication) {
		CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();

		AddCampaign campaign = service.getAdd(user.getUsername(), CampaignType.POST);

		if (campaign == null)
			return ResponseEntity.noContent().build();
		else
			return ResponseEntity.ok(campaign);
	}

}
