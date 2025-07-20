package com.example.board.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.board.model.board.Board;
import com.example.board.service.BoardService;
import com.example.board.util.PageNavigator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("board")
@Controller
public class BoardController {
	
	private final BoardService boardService;
	
	final int countPerPage = 10;
	final int pagePerGroup = 5;

	// 게시글 전체보기
	@GetMapping("list")
	public String list(@RequestParam(value = "page", defaultValue = "1") int page,
						@RequestParam(value = "searchText", defaultValue = "") String searchText,
						Model model) {
		
		log.info("searchText: {}", searchText);
		
		int total = boardService.getTotal(searchText);
		PageNavigator navi = new PageNavigator(countPerPage, pagePerGroup, page, total);
		
		List<Board> boards = boardService.findBoards(searchText, navi.getStartRecord(), navi.getCurrentPage());
		
		model.addAttribute("boards", boards);
		model.addAttribute("navi", navi);
		model.addAttribute("searchText", searchText);
		
		return "board/list";
	}
	
}
