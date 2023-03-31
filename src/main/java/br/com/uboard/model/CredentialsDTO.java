package br.com.uboard.model;

import java.io.Serializable;

public class CredentialsDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String address;
	private String token;
	private String userUUID;

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getUserUUID() {
		return userUUID;
	}

	public void setUserUUID(String userUUID) {
		this.userUUID = userUUID;
	}

}
