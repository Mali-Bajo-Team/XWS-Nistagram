package com.xws.adds.dto;

import java.util.Date;

import com.xws.adds.dto.post.Post;
import com.xws.adds.model.InterestGroupDescription;

public class MultipleCampaignDTO {

	private Post post;
	private Boolean isStory;
	private Date startDate;
	private Date endDate;
	private Integer timesPerDay;
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

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Integer getTimesPerDay() {
		return timesPerDay;
	}

	public void setTimesPerDay(Integer timesPerDay) {
		this.timesPerDay = timesPerDay;
	}

	public InterestGroupDescription getInterestGroup() {
		return interestGroup;
	}

	public void setInterestGroup(InterestGroupDescription interestGroup) {
		this.interestGroup = interestGroup;
	}

}
