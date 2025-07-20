package com.example.board.model.board;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class BoardUpdateForm {

	private Long board_id;
	@NotBlank
	private String title;
	@NotBlank
	private String contents;
	private String member_id;
	private Long hit;
	private LocalDateTime created_time;
	private boolean isFileRemoved;
	
}
