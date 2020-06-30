package com.example.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.board.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

	/* �̸��� ������ ���̵� ��ȸ */
	public User findByEmail(String email);

	/* �α��� - �̸��ϰ� ��й�ȣ�� ��ȸ */
	public User findByEmailAndPwd(String email, String pwd);
}