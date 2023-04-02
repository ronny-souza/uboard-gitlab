package br.com.uboard.model.enums;

public enum GitlabPaginationEnum {

	TOTAL_PAGES("X-Total-Pages");

	private String name;

	GitlabPaginationEnum(String paginationParam) {
		this.name = paginationParam;
	}

	public String getName() {
		return name;
	}
}
