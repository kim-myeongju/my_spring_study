package com.example.board.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.board.model.board.Reply;

@Mapper
public interface ReplyMapper {

	void saveReply(Reply reply);
	Reply findReply(Long reply_id);
	List<Reply> findReplies(Long board_id);
	void updateReply(Reply reply);
	void removeReply(Long reply_id);
	
}
