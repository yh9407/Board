package com.example.board.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
	public String signupPost(@ModelAttribute User user, Model model) {

		/* �ߺ� ���̵� ���� �Ұ��� ���ؼ� ���Կ��� Ȯ�� */
		User findUser = userRepository.findByEmail(user.getEmail());
		System.out.println("@@@@@@@@@@@@" + findUser);
		if (findUser == null) {
			userRepository.save(user);
		} else {
			model.addAttribute("email", user.getEmail());
			model.addAttribute("name", user.getName());
			return "signup_error2";
		}
		return "redirect:/";

		/*
		 * �����丮 . �� ���
		 * if (findUser == null) { userRepository.save(user); } else { return
		 * "signup_error"; //html ����,(signup_error) �̷������� ���� �ߺ��� �̸��� �Է½� �ٸ� �Էµ� �͵鵵 ��
		 * ������� �ڷ� �� �׷��� ajax�� ���°��� ���� } return "redirect:/";
		 */

	}

	@GetMapping("/signout")
	public String signout() {
		session.removeAttribute("user_info"); // ������ ���ǰ��� ����
		// sessin.invalidate(); ���Ǿȿ� ��� ���� ����
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