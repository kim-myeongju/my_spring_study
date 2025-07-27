package com.example.board.controller;

import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpHeaders;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriUtils;

import com.example.board.model.board.AttachedFile;
import com.example.board.model.board.Board;
import com.example.board.model.board.BoardWriteForm;
import com.example.board.model.member.Member;
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
	
	@Value("${file.upload.path}")
	private String uploadPath;

	// 게시글 전체보기
	@GetMapping("list")
	public String list(@RequestParam(value = "page", defaultValue = "1") int page,
						@RequestParam(value = "searchText", defaultValue = "") String searchText,
						Model model) {
		
		log.info("searchText: {}", searchText);
		
		int total = boardService.getTotal(searchText);
		PageNavigator navi = new PageNavigator(countPerPage, pagePerGroup, page, total);
		
		List<Board> boards = boardService.findBoards(searchText, navi.getStartRecord(), navi.getCountPerPage());
		
		model.addAttribute("boards", boards);
		model.addAttribute("navi", navi);
		model.addAttribute("searchText", searchText);
		
		return "board/list";
	}
	
	// 게시글 작성
	@GetMapping("write")
	public String writeForm(Model model) {
		
		model.addAttribute("writeForm", new BoardWriteForm());
		
		return "board/write";
	}
	
	@PostMapping("write")
	public String write(@SessionAttribute(value = "loginMember", required = false) Member loginMember,
						@Validated @ModelAttribute("writeForm") BoardWriteForm boardWriteForm,
						BindingResult result,
						@RequestParam(required = false) MultipartFile file) {
		
		if(loginMember == null) {
			return "redirect:/member/login";
		}
		
		if(result.hasErrors()) {
			return "redirect:/write";
		}
		
		Board board = BoardWriteForm.toBoard(boardWriteForm);
		board.setMember_id(loginMember.getMember_id());
		
		boardService.savedBoard(board, file);
		
		return "redirect:/board/list";
	}
	
	@GetMapping("read")
	public String read(@RequestParam Long board_id, Model model) {
		
		// board_id에 해당하는 게시글을 데이터베이스에서 찾는다.(조회수 수정)
		Board board = boardService.readBoard(board_id);
		if(board == null) {
			log.info("게시글 없음");
			return "redirect:/board/list";
		}
		
		model.addAttribute("board", board);
		
		AttachedFile attachedFile = boardService.findFileByBoardId(board_id);
		model.addAttribute("file", attachedFile);
		
		return "board/read";
	}
	
	@GetMapping("download/{id}")
	public ResponseEntity<Resource> download(@PathVariable Long id) throws MalformedURLException {
		
		AttachedFile attachedFile = boardService.findFileByAttachedFileId(id);
		String fullPath = uploadPath + "/" + attachedFile.getSaved_filename();
		UrlResource resource = new UrlResource("file:" + fullPath);
		String encodingFileName = UriUtils.encode(attachedFile.getOriginal_filename(), StandardCharsets.UTF_8);
		String contentDisposition = "attachment; filename=\"" + encodingFileName + "\"";
		
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition).body(resource);
	}
	
}
