package com.example.board.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.example.board.model.board.Reply;
import com.example.board.model.board.ReplyDto;
import com.example.board.model.member.Member;
import com.example.board.repository.ReplyMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("reply")
@RestController
public class ReplyRestController {

	private final ReplyMapper replyMapper;
	
	@PostMapping("{board_id}")
	public ResponseEntity<String> writeReply(@PathVariable Long board_id, @ModelAttribute Reply reply, @SessionAttribute("loginMember") Member loginMember) {
		
		reply.setBoard_id(board_id);
		reply.setMember_id(loginMember.getMember_id());
		
		replyMapper.saveReply(reply);
		
		return ResponseEntity.ok("리플 등록 성공");
	}
	
	@GetMapping("{board_id}")
	public ResponseEntity<List<ReplyDto>> findReplies(@SessionAttribute("loginMember") Member loginMember, @PathVariable Long board_id) {
		
		List<Reply> replies = replyMapper.findReplies(board_id);
		List<ReplyDto> replyDtos = new ArrayList<>();
		if(replies != null && replies.size() > 0) {
			for(Reply reply : replies) {
				ReplyDto replyDto = Reply.toDto(reply);
				if(reply.getMember_id().equals(loginMember.getMember_id())) {
					replyDto.setWriter(true);
				}
				replyDtos.add(replyDto);
			}
		}
		
		return ResponseEntity.ok(replyDtos);
	}
	
}
