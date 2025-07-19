package com.example.board.model.member;

import java.time.LocalDate;

import javax.validation.constraints.*;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MemberJoinForm {

	@Size(min = 4, max = 20)
	private String member_id;
	@Size(min = 4, max = 20)
	private String password;
	@NotBlank
	private String name;
	@NotNull
	private GenderType gender;
	@Past
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate birth;
	private String email;
	
	public static Member toMember(MemberJoinForm memberJoinForm) {
		Member member = new Member();
		member.setMember_id(memberJoinForm.getMember_id());
		member.setPassword(memberJoinForm.getPassword());
		member.setName(memberJoinForm.getName());
		member.setGender(memberJoinForm.getGender());
		member.setBirth(memberJoinForm.getBirth());
		member.setEmail(memberJoinForm.getEmail());
		return member;
	}
	
}
