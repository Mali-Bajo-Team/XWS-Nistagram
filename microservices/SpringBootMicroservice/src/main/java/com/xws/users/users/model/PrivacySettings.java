package com.xws.users.users.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class PrivacySettings {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private boolean isPrivate;

	@Column(nullable = false)
	private boolean allowMessagesFromNotFollowed;

	@Column(nullable = false)
	private boolean allowTags;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public boolean isPrivate() {
		return isPrivate;
	}

	public void setPrivate(boolean aPrivate) {
		isPrivate = aPrivate;
	}

	public boolean isAllowMessagesFromNotFollowed() {
		return allowMessagesFromNotFollowed;
	}

	public void setAllowMessagesFromNotFollowed(boolean allowMessagesFromNotFollowed) {
		this.allowMessagesFromNotFollowed = allowMessagesFromNotFollowed;
	}

	public boolean isAllowTags() {
		return allowTags;
	}

	public void setAllowTags(boolean allowTags) {
		this.allowTags = allowTags;
	}

}
