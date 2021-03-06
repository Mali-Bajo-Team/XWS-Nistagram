package com.xws.adds.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.xws.adds.dto.MultipleCampaignDTO;
import com.xws.adds.dto.MultipleCampaignUpdateDTO;
import com.xws.adds.dto.OneTimeCampaignDTO;
import com.xws.adds.model.AddCampaign;
import com.xws.adds.security.CustomUserDetails;
import com.xws.adds.service.ICampaignService;

@RestController
public class CampaignController {

	@Autowired
	private ICampaignService campaignService;

	@PreAuthorize("hasAnyRole('AGENT', 'API')")
	@PostMapping("campaigns/multiple")
	public ResponseEntity<AddCampaign> createMultiple(@RequestBody MultipleCampaignDTO campaign,
			Authentication authentication) {
		CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();

		AddCampaign addCampaign = campaignService.createMultipleCampaign(campaign, user.getUsername());

		return ResponseEntity.ok(addCampaign);
	}

	@PreAuthorize("hasAnyRole('AGENT', 'API')")
	@PostMapping("campaigns/onetime")
	public ResponseEntity<AddCampaign> createOneTime(@RequestBody OneTimeCampaignDTO campaign,
			Authentication authentication) {
		CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();

		AddCampaign addCampaign = campaignService.createOneTimeCampaign(campaign, user.getUsername());

		return ResponseEntity.ok(addCampaign);
	}

	@PreAuthorize("hasAnyRole('AGENT', 'API')")
	@GetMapping("campaigns")
	public ResponseEntity<List<AddCampaign>> getCampaigns(Authentication authentication) {
		CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();

		return ResponseEntity.ok(campaignService.getCampaigns(user.getUsername()));
	}

	@PreAuthorize("hasAnyRole('AGENT', 'API')")
	@DeleteMapping("campaigns/{id}")
	public ResponseEntity<Void> getCampaigns(@PathVariable(required = true) Long id, Authentication authentication) {
		CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();

		campaignService.deleteCampaign(id, user.getUsername());

		return ResponseEntity.noContent().build();
	}

	@PreAuthorize("hasAnyRole('AGENT', 'API')")
	@PutMapping("campaigns/{id}")
	public ResponseEntity<AddCampaign> getCampaigns(@PathVariable(required = true) Long id,
			Authentication authentication, @RequestBody MultipleCampaignUpdateDTO update) {
		CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();

		return ResponseEntity.ok(campaignService.updateCampaign(id, user.getUsername(), update));
	}
	
	@PreAuthorize("hasRole('INFLUENCER')")
	@GetMapping("campaigns/pending")
	public ResponseEntity<List<AddCampaign>> getPendingCampaigns(Authentication authentication) {
		CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();

		return ResponseEntity.ok(campaignService.getPendingCampaigns(user.getUsername()));
	}
	
	@PreAuthorize("hasRole('INFLUENCER')")
	@PostMapping("campaigns/pending/{id}/accept")
	public ResponseEntity<Void> acceptCampaign(@PathVariable(required = true) Long id,
			Authentication authentication) {
		CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();
		
		campaignService.acceptCampaign(user.getUsername(), id);

		return ResponseEntity.noContent().build();
	}

	@PreAuthorize("hasRole('INFLUENCER')")
	@PostMapping("campaigns/pending/{id}/reject")
	public ResponseEntity<Void> rejectCampaign(@PathVariable(required = true) Long id,
			Authentication authentication) {
		CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();
		
		campaignService.rejectCampaign(user.getUsername(), id);

		return ResponseEntity.noContent().build();
	}
	
}
