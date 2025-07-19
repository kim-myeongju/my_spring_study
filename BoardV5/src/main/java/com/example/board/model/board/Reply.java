package com.example.board.model.board;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Reply {

	private Long reply_id;
	private Long board_id;
	private String member_id;
	private String content;
	private LocalDateTime created_time;
	
}
