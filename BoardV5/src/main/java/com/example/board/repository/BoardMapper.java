package com.example.board.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import com.example.board.model.board.AttachedFile;
import com.example.board.model.board.Board;

@Mapper
public interface BoardMapper {

	void saveBoard(Board board);
	int getTotal(String searchText);
	List<Board> findBoards(String searchText, RowBounds rowBounds);
	Board findBoard(Long board_id);
	void updateBoard(Board updateBoard);
	void removeBoard(Long board_id);
	void saveFile(AttachedFile attachedFile);
	AttachedFile findFileByBoardId(Long board_id);
	AttachedFile fineFileByAttachedFileId(Long attachedFile_id);
	void removeAttachedFile(Long attachedFile_id);
	
}
