package br.com.uboard.model;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

public class GroupDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String name;

	@SerializedName("avatar_url")
	private String avatarUrl;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAvatarUrl() {
		return avatarUrl;
	}

	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}

}
