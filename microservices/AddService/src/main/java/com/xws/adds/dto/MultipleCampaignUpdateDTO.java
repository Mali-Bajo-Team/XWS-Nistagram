package com.xws.adds.dto;

import java.util.Date;

public class MultipleCampaignUpdateDTO {

	private Date startDate;
	private Date endDate;
	private Integer timesPerDay;

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

}
