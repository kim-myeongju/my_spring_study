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
	
	public static ReplyDto toDto(Reply reply) {
		ReplyDto dto = new ReplyDto();
		dto.setReply_id(reply.getReply_id());
		dto.setBoard_id(reply.getBoard_id());
		dto.setMember_id(reply.getMember_id());
		dto.setContent(reply.getContent());
		dto.setCreated_time(reply.getCreated_time());
		
		return dto;
	}
	
}
