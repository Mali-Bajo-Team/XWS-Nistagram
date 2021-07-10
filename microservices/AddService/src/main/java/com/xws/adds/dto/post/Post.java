package com.xws.adds.dto.post;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Post {

	private String title;
	private String description;
	@JsonProperty("add_link")
	private String addLink;
	@JsonProperty("is_add")
	private Boolean isAdd;
	private List<Content> content;
	private List<String> hashtags;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAddLink() {
		return addLink;
	}

	public void setAddLink(String addLink) {
		this.addLink = addLink;
	}

	public Boolean getIsAdd() {
		return isAdd;
	}

	public void setIsAdd(Boolean isAdd) {
		this.isAdd = isAdd;
	}

	public List<Content> getContent() {
		return content;
	}

	public void setContent(List<Content> content) {
		this.content = content;
	}

	public List<String> getHashtags() {
		return hashtags;
	}

	public void setHashtags(List<String> hashtags) {
		this.hashtags = hashtags;
	}

}
