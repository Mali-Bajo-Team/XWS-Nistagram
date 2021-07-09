package com.xws.adds.model.events;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "discriminator", discriminatorType = DiscriminatorType.STRING)
public class AddEvent {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column
	private Date timestamp;

	@Column
	private Long campaignId;

	@Column
	private String addLink;

	@Column
	private Boolean isStory;

	@Column
	private String postId;

	@Column
	private String advertiserUsername;

	@Column
	private Boolean isAdvertiserInfluencer;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public Long getCampaignId() {
		return campaignId;
	}

	public void setCampaignId(Long campaignId) {
		this.campaignId = campaignId;
	}

	public String getAddLink() {
		return addLink;
	}

	public void setAddLink(String addLink) {
		this.addLink = addLink;
	}

	public Boolean getIsStory() {
		return isStory;
	}

	public void setIsStory(Boolean isStory) {
		this.isStory = isStory;
	}

	public String getPostId() {
		return postId;
	}

	public void setPostId(String postId) {
		this.postId = postId;
	}

	public String getAdvertiserUsername() {
		return advertiserUsername;
	}

	public void setAdvertiserUsername(String advertiserUsername) {
		this.advertiserUsername = advertiserUsername;
	}

	public Boolean getIsAdvertiserInfluencer() {
		return isAdvertiserInfluencer;
	}

	public void setIsAdvertiserInfluencer(Boolean isAdvertiserInfluencer) {
		this.isAdvertiserInfluencer = isAdvertiserInfluencer;
	}

}
