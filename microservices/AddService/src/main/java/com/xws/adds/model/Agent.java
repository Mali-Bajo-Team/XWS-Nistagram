package com.xws.adds.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

@Entity
@DiscriminatorValue("Agent")
public class Agent extends Advertiser {

	@Column
	private String webSite;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "agent")
	private List<Collaboration> influencers;

	public String getWebSite() {
		return webSite;
	}

	public void setWebSite(String webSite) {
		this.webSite = webSite;
	}

	public List<Collaboration> getInfluencers() {
		return influencers;
	}

	public void setInfluencers(List<Collaboration> influencers) {
		this.influencers = influencers;
	}

}
