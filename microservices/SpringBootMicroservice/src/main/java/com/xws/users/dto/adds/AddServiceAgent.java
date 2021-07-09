package com.xws.users.dto.adds;

import com.xws.users.users.model.roles.RegularUser;

public class AddServiceAgent {

	private Long id;
	private String username;
	private String webSite;
	
	

	public AddServiceAgent(RegularUser user) {
		super();
		this.id = user.getId();
		this.username = user.getUsername();
		this.webSite = user.getLinkToWebSite();
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

	public String getWebSite() {
		return webSite;
	}

	public void setWebSite(String webSite) {
		this.webSite = webSite;
	}

}
