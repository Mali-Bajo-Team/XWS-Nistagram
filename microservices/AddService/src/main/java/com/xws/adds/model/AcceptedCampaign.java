package com.xws.adds.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class AcceptedCampaign {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String postId;

	private AddCampaign campaign;

	public String getPostId() {
		return postId;
	}

	public void setPostId(String postId) {
		this.postId = postId;
	}

	public AddCampaign getCampaign() {
		return campaign;
	}

	public void setCampaign(AddCampaign campaign) {
		this.campaign = campaign;
	}

}
