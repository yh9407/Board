package com.example.board.controller;

import java.util.List;
import java.util.Optional;

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
import org.springframework.web.bind.annotation.PathVariable;
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
	@GetMapping("/update/{id}")  // id ��� ��Ī�� �����ڰ� ���Ƿ� ���� ��Ī��.
	public String update(@PathVariable("id") long id, Model model) {
		System.out.println("@@@@@@@@@@@@@@@@" + id);
		
		Optional<Board> opt = boardRepository.findById(id);
		Board board = opt.get();
		System.out.println(board);
		model.addAttribute("board", board);

		
		return "board/update";
	}	
	
	@PostMapping("/update")
	@ResponseBody //���ڿ��� ����
	public String boardUpdatePost(@ModelAttribute Board board) {
		System.out.println(board);
		// board�� Ű (id)�� �ߺ��̸� .. ������� ����
		// board�� Ű (id)�� �ߺ��� �ƴ϶�� .. �Է� ��� ����
		// ==================>>>>>>>>>jpa�� ���
				
		boardRepository.save(board); //�Է� �Ǵ� ����
//		/* �α��� ���� Ȯ�� (������ �� Ȯ��) */
//		User user = (User) session.getAttribute("user_info");
//		if(user == null) {
//			return "0";
//		}
//		else {
//		String userId = user.getEmail();
//		board.setUserId(userId);
//		boardRepository.save(board);
//		}
		return "1";
	}
	@GetMapping("/delete/{id}")
	public String delete(
			@PathVariable ("id") long id) {
		boardRepository.deleteById(id);
	return "redirect:/board/";	
	}
	
	
	
	
@GetMapping("/{id}")  // id ��� ��Ī�� �����ڰ� ���Ƿ� ���� ��Ī��.
public String detail(@PathVariable("id") long id, Model model) {
	System.out.println("@@@@@@@@@@@@@@@@" + id);
	
	Optional<Board> opt = boardRepository.findById(id);
	Board board = opt.get();
	System.out.println(board);
	model.addAttribute("board", board);

	
	return "board/detail";
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
		model.addAttribute("totalPage", totalPage);
		model.addAttribute("page", page);
		return "board/list";
	}

}
/*    */