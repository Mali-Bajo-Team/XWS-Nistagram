package com.xws.adds.dto;

import java.util.Date;

import com.xws.adds.dto.post.Post;
import com.xws.adds.model.InterestGroupDescription;

public class OneTimeCampaignDTO {

	private Post post;
	private Boolean isStory;
	private Date exposureTime;
	private InterestGroupDescription interestGroup;

	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}

	public Boolean getIsStory() {
		return isStory;
	}

	public void setIsStory(Boolean isStory) {
		this.isStory = isStory;
	}

	public Date getExposureTime() {
		return exposureTime;
	}

	public void setExposureTime(Date exposureTime) {
		this.exposureTime = exposureTime;
	}

	public InterestGroupDescription getInterestGroup() {
		return interestGroup;
	}

	public void setInterestGroup(InterestGroupDescription interestGroup) {
		this.interestGroup = interestGroup;
	}

}
