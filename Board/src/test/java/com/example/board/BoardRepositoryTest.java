package com.example.board;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.board.model.Board;
import com.example.board.repository.BoardRepository;

@SpringBootTest
class BoardRepositoryTest {
	@Autowired
	BoardRepository br;

	@Test
	void �Խñ�100���Է��ϱ�() {
		for (int i = 0; i <= 100; i++) {
			
		Board board = new Board();
		board.setTitle( i + "�� ����");
		board.setContent(i + "������");
		board.setUserId("dudgh9407@naver.com");
		br.save(board);
		}
		
	}
	
	@Test
	void contextLoads2() {
		System.out.println("aaaa");
	}

}
