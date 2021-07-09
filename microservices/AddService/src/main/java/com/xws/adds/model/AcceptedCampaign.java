package com.xws.adds.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class AcceptedCampaign {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String postId;

	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	private AddCampaign campaign;

	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	private Influencer influencer;

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

	public Influencer getInfluencer() {
		return influencer;
	}

	public void setInfluencer(Influencer influencer) {
		this.influencer = influencer;
	}

}
