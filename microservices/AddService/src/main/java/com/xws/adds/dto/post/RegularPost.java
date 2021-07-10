package com.xws.adds.dto.post;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RegularPost {
	@JsonProperty("my_post")
	private Post post;
	@JsonProperty("_id")
	private String id;

	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
