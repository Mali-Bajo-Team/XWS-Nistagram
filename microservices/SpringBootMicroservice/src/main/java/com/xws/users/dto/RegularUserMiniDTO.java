package com.xws.users.dto;

import com.xws.users.users.model.roles.RegularUser;

public class RegularUserMiniDTO {

	private Long id;
	private String username;
	private String profileImagePath;

	public RegularUserMiniDTO(RegularUser user) {
		super();
		this.id = user.getId();
		this.username = user.getUsername();
		this.profileImagePath = user.getProfileImagePath();
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

}
