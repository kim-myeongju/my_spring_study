package com.example.board.model.member;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Member {

	private String member_id;
	private String password;
	private String name;
	private GenderType gender;
	private LocalDate birth;
	private String email;
	
}
