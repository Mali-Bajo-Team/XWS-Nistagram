package com.xws.users.dto.adds;

import com.xws.users.users.model.roles.RegularUser;

public class Influencer {

	private Long id;
	private String username;

	public Influencer(RegularUser user) {
		super();
		this.id = user.getId();
		this.username = user.getUsername();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
