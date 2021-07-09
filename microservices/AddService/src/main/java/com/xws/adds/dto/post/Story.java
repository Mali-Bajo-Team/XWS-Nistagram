package com.xws.adds.dto.post;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Story {
	@JsonProperty("my_post")
	private Post post;
	@JsonProperty("is_for_close_friends")
	private Boolean isForClose;
	@JsonProperty("_id")
	private String id;

	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}

	public Boolean getIsForClose() {
		return isForClose;
	}

	public void setIsForClose(Boolean isForClose) {
		this.isForClose = isForClose;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
