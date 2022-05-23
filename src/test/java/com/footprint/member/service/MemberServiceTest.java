package com.footprint.member.service;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import com.footprint.common.exception.BaseExceptionType;
import com.footprint.member.domain.Member;
import com.footprint.member.exception.MemberException;
import com.footprint.member.exception.MemberExceptionType;
import com.footprint.member.service.dto.CreateMemberDto;

/**
 * Created by ShinD on 2022/05/14.
 */
@SpringBootTest
@Transactional
class MemberServiceTest {


	@Autowired EntityManager em;
	@Autowired MemberService memberService;
	@Autowired PasswordEncoder passwordEncoder;


	@Test
	@DisplayName("회원가입 - 성공")
	public void successSignUp() throws Exception {
	    //given
		String username = "username";
		String password = "password";
		String nickName = "nickName";

		CreateMemberDto createMemberDto = new CreateMemberDto(username, password, nickName);

		//when
		memberService.create(createMemberDto);

		em.flush();
		em.clear();

	    //then
		Member findMember = em.createQuery("SELECT m FROM Member m WHERE m.username = :username", Member.class)
			.setParameter("username", username)
			.getSingleResult();

		assertThat(findMember).isNotNull();
		assertThat(findMember.getId()).isNotNull();
		assertThat(findMember.getNickname()).isEqualTo(nickName);
		assertThat(passwordEncoder.matches(password, findMember.getPassword())).isTrue();
		assertThat(findMember.getPassword()).startsWith("{bcrypt}");

	}


	@Test
	@DisplayName("회원가입 - 실패 (원인 : 아이디 중복)")
	public void failSignUpSinceDuplicatedUsername() throws Exception {
		//given
		String username = "username";
		String password = "password";
		String nickName = "nickName";

		CreateMemberDto createMemberDto = new CreateMemberDto(username, password, nickName);
		memberService.create(createMemberDto);
		em.flush();
		em.clear();

		//when, then
		BaseExceptionType exceptionType = assertThrows(MemberException.class,
														() -> memberService.create(createMemberDto)).getExceptionType();

		assertThat(exceptionType).isEqualTo(MemberExceptionType.DUPLICATE_USERNAME);
		List<Member> members = em.createQuery("SELECT m FROM Member m", Member.class).getResultList();
		assertThat(members.size()).isEqualTo(1);

	}

}