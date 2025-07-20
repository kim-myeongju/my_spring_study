package com.example.board.service;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.board.model.board.AttachedFile;
import com.example.board.model.board.Board;
import com.example.board.repository.BoardMapper;
import com.example.board.util.FileService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class BoardService {
	
	private final BoardMapper boardMapper;
	private final FileService fileService;
	
	public int getTotal(String searchText) {
		return boardMapper.getTotal(searchText);
	}
	
	public List<Board> findBoards(String searchText, int startRecord, int countPerPage) {
		RowBounds rowBounds = new RowBounds(startRecord, countPerPage);
		return boardMapper.findBoards(searchText, rowBounds);
	}
	
	public void savedBoard(Board board, MultipartFile file) {
		boardMapper.saveBoard(board);
		if(file != null && file.getSize() > 0) {
			AttachedFile attachedFile = fileService.savedFile(file);
			attachedFile.setBoard_id(board.getBoard_id());
			boardMapper.saveFile(attachedFile);
		}
	}

}
