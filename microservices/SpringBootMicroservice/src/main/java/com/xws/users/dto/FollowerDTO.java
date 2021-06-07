package com.xws.users.dto;

import com.xws.users.users.model.roles.RegularUser;

public class FollowerDTO {

	private Long id;
	private String username;
	private String profileImagePath;
	private Boolean isClose;

	public FollowerDTO(RegularUser user, Boolean isClose) {
		super();
		this.id = user.getId();
		this.username = user.getUsername();
		this.profileImagePath = user.getProfileImagePath();
		this.isClose = isClose;
	}

	public Long getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public String getProfileImagePath() {
		return profileImagePath;
	}

	public Boolean getIsClose() {
		return isClose;
	}

}
