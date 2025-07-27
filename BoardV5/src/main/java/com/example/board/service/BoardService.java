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
	
	public Board findBoard(Long board_id) {
		return boardMapper.findBoard(board_id);
	}
	
	public Board readBoard(Long board_id) {
		
		Board board = findBoard(board_id);
		board.addHit();
		updateBoard(board, false, null);
		
		return board;
	}
	
	public void updateBoard(Board updateBoard, boolean isFileRemoved, MultipartFile file) {
		Board board = findBoard(updateBoard.getBoard_id());
		
		if(board != null) {
			boardMapper.updateBoard(board);
			
			AttachedFile attachedFile = boardMapper.findFileByBoardId(updateBoard.getBoard_id());
			
			if(attachedFile != null && (isFileRemoved || (file != null && file.getSize() > 0))) {
				// 파일 삭제를 요청했거나 새로운 파일이 업로드 되면 기존 파일을 삭제한다.
				removeAttachedFile(attachedFile.getAttachedFile_id());
			}
			
			// 새로 저장할 파일이 있으면 저장한다.
			if(file != null && file.getSize() > 0) {
				
			}
			
		}
	}
	
	public void removeAttachedFile(Long attachedFile_id) {
		
	}
	
	public AttachedFile findFileByBoardId(Long board_id) {
		return boardMapper.findFileByBoardId(board_id);
	}
	
	public AttachedFile findFileByAttachedFileId(Long attachedFile_id) {
		return boardMapper.fineFileByAttachedFileId(attachedFile_id);
	}

}
