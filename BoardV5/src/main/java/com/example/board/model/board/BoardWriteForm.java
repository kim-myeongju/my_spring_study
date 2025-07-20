package com.example.board.model.board;

import javax.validation.constraints.NotBlank;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class BoardWriteForm {

	@NotBlank
	private String title;
	@NotBlank
	private String contents;
	private MultipartFile attachedFile;
	
	public static Board toBoard(BoardWriteForm boardWriteForm) {
		Board board = new Board();
		board.setTitle(boardWriteForm.getTitle());
		board.setContents(boardWriteForm.getContents());
		return board;
	}
	
}
