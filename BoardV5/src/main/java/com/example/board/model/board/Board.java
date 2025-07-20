package com.example.board.model.board;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Board {

	private Long board_id;
	private String title;
	private String contents;
	private String member_id;
	private Long hit;
	private LocalDateTime created_time;
	
	public static BoardUpdateForm toBoardUpdateForm(Board board) {
		BoardUpdateForm boardUpdateForm = new BoardUpdateForm();
		boardUpdateForm.setBoard_id(board.getBoard_id());
		boardUpdateForm.setTitle(board.getTitle());
		boardUpdateForm.setContents(board.getContents());
		boardUpdateForm.setMember_id(board.getMember_id());
		boardUpdateForm.setHit(board.getHit());
		boardUpdateForm.setCreated_time(board.getCreated_time());
		return boardUpdateForm;
	}
	
	public void addHit() {
		this.hit++;
	}
	
}
