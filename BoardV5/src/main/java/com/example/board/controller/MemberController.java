package com.example.board.controller;

import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.board.model.member.LoginForm;
import com.example.board.model.member.Member;
import com.example.board.model.member.MemberJoinForm;
import com.example.board.repository.MemberMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("member")
@Controller
public class MemberController {

	private final MemberMapper memberMapper;
	
	@GetMapping("login")
	public String loginForm(Model model) {
		model.addAttribute("loginForm", new LoginForm());
		
		return "member/loginForm";
	}
	
	@PostMapping("login")
	public String login(@Validated @ModelAttribute("loginForm") LoginForm loginForm,
																BindingResult result,
																HttpServletRequest request,
																@RequestParam(defaultValue = "/") String redirectURL) {
		
		log.info("redirectURL: {}", redirectURL);
		log.info("loginForm: {}", loginForm);
		
		if(result.hasErrors()) {
			return "member/loginForm";
		}
		
		Member member = memberMapper.findMember(loginForm.getMember_id());
		
		if(member == null || !member.getPassword().equals(loginForm.getPassword())) {
			result.reject("loginError", "아이디가 없거나 패스워드가 다릅니다.");
			return "member/loginForm";
		}
		
		HttpSession session = request.getSession();
		session.setAttribute("loginMember", member);
		
		return "redirect:" + redirectURL;
	}
	
	@GetMapping("logout")
	public String logout(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		
		if(session != null) {
			session.invalidate();
		}
		
		return "redirect:/";
		
	}
	
	@GetMapping("join")
	public String joinForm(Model model) {
		model.addAttribute("joinForm", new MemberJoinForm());
		return "member/joinForm";
	}
	
	@PostMapping("join")
	public String join(@Validated @ModelAttribute("joinForm") MemberJoinForm joinForm, BindingResult result) {
		
		log.info("joinForm: {}", joinForm);
		
		if(result.hasErrors()) {
			return "member/joinForm";
		}
		
		if(!joinForm.getEmail().contains("@")) {
			result.reject("emailError", "이메일 형식이 잘못되었습니당");
			return "member/joinForm";
		}
		
		Member member = memberMapper.findMember(joinForm.getMember_id());
		
		if(member != null) {
			log.info("이미 가입된 아이디");
			
			result.reject("duplicate ID", "이미 가입된 아이디 입니다.");
			
			return "member/joinForm";
		}
		
		memberMapper.saveMember(MemberJoinForm.toMember(joinForm));
		
		return "redirect:/";
	}
	
}
