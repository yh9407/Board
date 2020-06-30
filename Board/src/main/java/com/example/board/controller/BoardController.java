package com.example.board.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.board.model.Board;
import com.example.board.model.User;
import com.example.board.repository.BoardRepository;

@Controller
@RequestMapping("/board")
public class BoardController {
	@Autowired
	BoardRepository boardRepository;
	@Autowired
	HttpSession session;

	@GetMapping("/write")
	public String boardWrite() {
		return "board/write";
	}

	@PostMapping("/write")
	public String boardWritePost(@ModelAttribute Board board) {
		User user = (User) session.getAttribute("user_info");
		String userId = user.getEmail();

		board.setUserId(userId);
		boardRepository.save(board);
		return "board/write";
	}

	@GetMapping("/board")
	public String board(Model model) {
		List<Board> list = boardRepository.findAll();
		model.addAttribute("list", list);
		return "board/list";
	}

}