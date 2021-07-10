package com.xws.users.dto.adds;

import java.util.Date;

import com.xws.users.users.model.roles.RegularUser;

public class UserUpdateDTO {

	private String oldUsername;
	private String newUsername;
	private Date dateOfBirth;
	private String gender;
	private String userCategory;
	private String webSiteLink;
	
	

	public UserUpdateDTO(String oldUsername, RegularUser regularUserForUpdate) {
		super();
		this.oldUsername = oldUsername;
		this.setNewUsername(regularUserForUpdate.getUsername());
		this.setDateOfBirth(regularUserForUpdate.getDateOfBirth());
		this.setGender(regularUserForUpdate.getGender());
		this.setWebSiteLink(regularUserForUpdate.getLinkToWebSite());
		if (regularUserForUpdate.getUserCategory() != null)
			this.setUserCategory(regularUserForUpdate.getUserCategory().getName());
	}

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
