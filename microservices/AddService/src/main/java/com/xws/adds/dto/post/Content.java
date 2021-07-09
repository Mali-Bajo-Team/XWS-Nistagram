package com.xws.adds.dto.post;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Content {

	@JsonProperty("_id")
	private String id;
	private String path;
	private String type;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
