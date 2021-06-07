package com.xws.users.dto;

import com.xws.users.users.model.enums.RelationshipType;
import com.xws.users.users.model.roles.RegularUser;
import com.xws.users.util.CollectionUtil;

public class UserProfileDTO {

	private Long id;
	private String username;
	private String bio;
	private String profileImagePath;
	private Boolean isPrivate;
	private Integer followerCount;
	private Integer followedCount;
	private String linkToWebsite;

	public UserProfileDTO(RegularUser user) {
		super();
		this.id = user.getId();
		this.username = user.getUsername();
		this.bio = user.getBio();
		this.profileImagePath = user.getProfileImagePath();
		this.isPrivate = user.getPrivacySettings().isPrivate();
		this.followerCount = CollectionUtil.findAll(user.getInRelationships(),
				relationship -> relationship.getRelationshipType().equals(RelationshipType.FOLLOWED)
						|| relationship.getRelationshipType().equals(RelationshipType.CLOSE_FRIEND))
				.size();
		this.followedCount = CollectionUtil.findAll(user.getOutRelationships(),
				relationship -> relationship.getRelationshipType().equals(RelationshipType.FOLLOWED)
						|| relationship.getRelationshipType().equals(RelationshipType.CLOSE_FRIEND))
				.size();
		this.linkToWebsite = user.getLinkToWebSite();
	}

	public Long getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public String getBio() {
		return bio;
	}

	public String getProfileImagePath() {
		return profileImagePath;
	}

	public Boolean getIsPrivate() {
		return isPrivate;
	}

	public Integer getFollowerCount() {
		return followerCount;
	}

	public Integer getFollowedCount() {
		return followedCount;
	}

	public String getLinkToWebsite() {
		return linkToWebsite;
	}

}
