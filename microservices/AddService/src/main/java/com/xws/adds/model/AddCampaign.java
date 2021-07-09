package com.xws.adds.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class AddCampaign {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private CampaignType type;

	@Column(nullable = false)
	private String postId;

	@Column(nullable = false)
	private Boolean oneTime;

	@ElementCollection
	@Column
	private List<Date> exposureTimes;

	@Column
	private Date startDate;

	@Column
	private Date endDate;

	@Column
	private Integer timesPerDay;

	@Column
	private Date lastEdited;

	@OneToOne(fetch = FetchType.LAZY, optional = true, cascade = CascadeType.ALL)
	private InterestGroupDescription interestGroup;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public CampaignType getType() {
		return type;
	}

	public void setType(CampaignType type) {
		this.type = type;
	}

	public String getPostId() {
		return postId;
	}

	public void setPostId(String postId) {
		this.postId = postId;
	}

	public Boolean getOneTime() {
		return oneTime;
	}

	public void setOneTime(Boolean oneTime) {
		this.oneTime = oneTime;
	}

	public List<Date> getExposureTimes() {
		return exposureTimes;
	}

	public void setExposureTimes(List<Date> exposureTimes) {
		this.exposureTimes = exposureTimes;
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

	public Date getLastEdited() {
		return lastEdited;
	}

	public void setLastEdited(Date lastEdited) {
		this.lastEdited = lastEdited;
	}

	public InterestGroupDescription getInterestGroup() {
		return interestGroup;
	}

	public void setInterestGroup(InterestGroupDescription interestGroup) {
		this.interestGroup = interestGroup;
	}

}
