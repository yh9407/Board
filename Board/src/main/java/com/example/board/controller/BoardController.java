package com.example.board.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
	@ResponseBody //���ڿ��� ����
	public String boardWritePost(@ModelAttribute Board board) {
		System.out.println(board);
		/* �α��� ���� Ȯ�� (������ �� Ȯ��) */
		User user = (User) session.getAttribute("user_info");
		if(user == null) {
			return "0";
		}
		else {
			
		String userId = user.getEmail();
		board.setUserId(userId);
		boardRepository.save(board);
		}
		
		return "1";
	}

	@GetMapping("/")
	public String board(
			Model model,
			@RequestParam(name = "page", defaultValue = "1") int page) {
		int startPage = (page - 1 )/10 *10 + 1 ; //int�� �Ҽ����� �������� �ʴ´�
		int endPage = startPage + 9 ;
		
		PageRequest req =
				PageRequest.of(
						page-1 , 10,
						Sort.by(Direction.DESC, "id"));
		
		Page<Board> result = boardRepository.findAll( req ); //����
		int totalPage = result.getTotalPages();  //���� �������Խñ��� �ִ� ������������ �����ϰ� ����¹�
		if(endPage > totalPage) {
			endPage = totalPage;
		}
		List<Board> list = result.getContent();
		
		
		model.addAttribute("list", list);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		model.addAttribute("page", page);
		return "board/list";
	}

}
/*    */