package com.example.board.model.member;

public enum GenderType {

	MALE("M"),
	FEMALE("F");
	
	private String description;
	
	private GenderType(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}
	
}
