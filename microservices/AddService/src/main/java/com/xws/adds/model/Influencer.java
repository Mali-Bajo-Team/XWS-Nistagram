package com.xws.adds.model;

import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("Influencer")
public class Influencer extends Advertiser {

	private List<Collaboration> agents;
	private List<CollaborationRequest> collaborationRequests;
	private List<AddCampaign> pendingCampaigns;
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
