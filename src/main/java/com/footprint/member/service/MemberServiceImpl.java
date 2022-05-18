package com.footprint.member.service;

import static com.footprint.member.exception.MemberExceptionType.*;

import java.util.ArrayList;

import javax.transaction.Transactional;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.footprint.member.domain.Member;
import com.footprint.member.exception.MemberException;
import com.footprint.member.repository.MemberRepository;
import com.footprint.member.service.dto.CreateMemberDto;

import lombok.RequiredArgsConstructor;

/**
 * Created by ShinD on 2022/05/14.
 */
@Service
@Transactional
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{

	private final MemberRepository memberRepository;
	private final PasswordEncoder passwordEncoder;

	/**
	 * 회원가입
	 */
	@Override
	public void create(CreateMemberDto createMemberDto) {

		//TODO 비밀번호를 암호화 하는 책임을 Member 에 둘지, encodePassword 에 둘지 고민
		createMemberDto.encodePassword(passwordEncoder);

		Member member = createMemberDto.toEntity();

		checkDuplicateUsername(member.getUsername());

		memberRepository.save(member);
	}



	/**
	 * 아이디 중복여부 체크
	 * 중복되었을 경우 MemberException 발생
	 */
	private void checkDuplicateUsername(String username) {
		memberRepository.findByUsername(username).ifPresent((member)-> {
			throw new MemberException(DUPLICATE_USERNAME);
		} );
	}



	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Member member = memberRepository.findByUsername(username)
										.orElseThrow(() -> new UsernameNotFoundException("회원을 찾을 수 없습니다."));

		return User.builder()
			.username(username)
			.password(member.getPassword())
			.authorities(new ArrayList<>()) //TODO 어드민 등의 관리자 계정이 따로 필요하다면 수정
			.build();
	}
}
