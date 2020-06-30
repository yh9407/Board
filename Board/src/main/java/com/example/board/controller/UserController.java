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

		/* �ߺ� ���̵� ���� �Ұ��� ���ؼ� ���Կ��� Ȯ�� */
		User findUser = userRepository.findByEmail(user.getEmail());
		System.out.println("@@@@@@@@@@@@" + findUser);
		if (findUser != null) {
			userRepository.save(user);
		} else {
			return "redirect:/signup";
			/* �̷������� ���� �ߺ��� �̸��� �Է½� �ٸ� �Էµ� �͵鵵 �� ������� �ڷ� �� �׷��� ajax�� ���°��� ���� */
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