package com.xws.users.dto.adds;

import java.util.Date;

import com.xws.users.users.model.roles.RegularUser;

public class AddConsumer {

	private Long id;
	private Date dateOfBirth;
	private String gender;
	private String userCategory;

	public AddConsumer(RegularUser user) {
		super();
		this.id = user.getId();
		this.dateOfBirth = user.getDateOfBirth();
		this.gender = user.getGender();
		if (user.getUserCategory() != null)
			this.userCategory = user.getUserCategory().getName();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

}
