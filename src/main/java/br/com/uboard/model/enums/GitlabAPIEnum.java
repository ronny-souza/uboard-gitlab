package br.com.uboard.model.enums;

public enum GitlabAPIEnum {

	USER("/user"),
	USERS("/users"),
	GROUPS("/groups"),
	PROJECTS("/projects");

	private String path;

	GitlabAPIEnum(String path) {
		this.path = path;
	}

	public String getPath() {
		return path;
	}

}
