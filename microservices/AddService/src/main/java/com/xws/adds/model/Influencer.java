package com.xws.adds.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@DiscriminatorValue("Influencer")
public class Influencer extends Advertiser {

	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "influencer")
	private List<Collaboration> agents;

	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "influencer")
	private List<CollaborationRequest> collaborationRequests;

	@JsonIgnore
	@ManyToMany(cascade = { CascadeType.ALL })
	@JoinTable(name = "influencer_campaign", joinColumns = @JoinColumn(name = "influencer_id"), inverseJoinColumns = @JoinColumn(name = "campaign_id"))
	private List<AddCampaign> pendingCampaigns;

	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "influencer")
	private List<AcceptedCampaign> acceptedCampaigns;

	public List<Collaboration> getAgents() {
		return agents;
	}

	public void setAgents(List<Collaboration> agents) {
		this.agents = agents;
	}

	public List<CollaborationRequest> getCollaborationRequests() {
		return collaborationRequests;
	}

	public void setCollaborationRequests(List<CollaborationRequest> collaborationRequests) {
		this.collaborationRequests = collaborationRequests;
	}

	public List<AddCampaign> getPendingCampaigns() {
		return pendingCampaigns;
	}

	public void setPendingCampaigns(List<AddCampaign> pendingCampaigns) {
		this.pendingCampaigns = pendingCampaigns;
	}

	public List<AcceptedCampaign> getAcceptedCampaigns() {
		return acceptedCampaigns;
	}

	public void setAcceptedCampaigns(List<AcceptedCampaign> acceptedCampaigns) {
		this.acceptedCampaigns = acceptedCampaigns;
	}

}
