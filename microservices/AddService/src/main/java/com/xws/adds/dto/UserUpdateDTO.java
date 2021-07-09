package com.xws.adds.dto;

import java.util.Date;

public class UserUpdateDTO {

	private String oldUsername;
	private String newUsername;
	private Date dateOfBirth;
	private String gender;
	private String userCategory;
	private String webSiteLink;

	public String getOldUsername() {
		return oldUsername;
	}

	public void setOldUsername(String oldUsername) {
		this.oldUsername = oldUsername;
	}

	public String getNewUsername() {
		return newUsername;
	}

	public void setNewUsername(String newUsername) {
		this.newUsername = newUsername;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getUserCategory() {
		return userCategory;
	}

	public void setUserCategory(String userCategory) {
		this.userCategory = userCategory;
	}

	public String getWebSiteLink() {
		return webSiteLink;
	}

	public void setWebSiteLink(String webSiteLink) {
		this.webSiteLink = webSiteLink;
	}

}
