package com.footprint.member.service.dto;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.footprint.member.domain.Member;

import lombok.Builder;
import lombok.Getter;

/**
 * Created by ShinD on 2022/05/14.
 */
@Getter
public class CreateMemberDto {

	private String username;
	private String password;
	private String nickname;

	@Builder
	public CreateMemberDto(String username, String password, String nickname) {
		this.username = username;
		this.password = password;
		this.nickname = nickname;
	}

	public void encodePassword(PasswordEncoder passwordEncoder) {
		this.password = passwordEncoder.encode(password);
	}

	public Member toEntity() {
		return Member.builder().username(username).password(password).nickname(nickname).build();
	}
}
