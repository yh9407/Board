package com.example.board.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import com.example.board.model.User;
import com.example.board.repository.UserRepository;

@Controller
public class UserController {
	@Autowired
	UserRepository userRepository;
	@Autowired
	HttpSession session;
	

	@GetMapping("/signup")

	public String signup() {
		return "signup";
	}

	@PostMapping("/signup")
	public String signupPost(@ModelAttribute User user) {

		/* 중복 아이디 가입 불가를 위해서 가입여부 확인 */
		User findUser = userRepository.findByEmail(user.getEmail());
		System.out.println("@@@@@@@@@@@@" + findUser);
		if (findUser != null) {
			userRepository.save(user);
		} else {
			return "redirect:/signup";
			/* 이런식으로 쓰면 중복된 이메일 입력시 다른 입력된 것들도 다 사라지고 뒤로 감 그래서 ajax를 쓰는것이 좋음 */
		}

		return "redirect:/";
	}

	
	@GetMapping("/signin")
	public String signin() {
		return "signin";
	}

	@PostMapping("/signin")
	public String signinPost(@ModelAttribute User user) {
		User dbUser = userRepository.findByEmailAndPwd(user.getEmail(), user.getPwd());
		if (dbUser != null) {
			session.setAttribute("user_info", dbUser);
		}
		return "redirect:/";
	}

}