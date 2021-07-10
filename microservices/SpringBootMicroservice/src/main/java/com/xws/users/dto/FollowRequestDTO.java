package com.xws.users.dto;

import java.util.Date;

import com.xws.users.users.model.FollowRequest;

public class FollowRequestDTO {

	private Long id;
	private Long requesterId;
	private String requesterUsername;
	private String requesterProfileImagePath;
	private Date creationDate;

	public FollowRequestDTO(FollowRequest followRequest) {
		super();
		this.id = followRequest.getId();
		this.requesterId = followRequest.getRequster().getId();
		this.requesterUsername = followRequest.getRequster().getUsername();
		this.requesterProfileImagePath = followRequest.getRequster().getProfileImagePath();
		this.creationDate = followRequest.getDateOfCreation();
	}

	public Long getRequesterId() {
		return requesterId;
	}

	public String getRequesterUsername() {
		return requesterUsername;
	}

	public String getRequesterProfileImagePath() {
		return requesterProfileImagePath;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public Long getId() {
		return id;
	}

}
