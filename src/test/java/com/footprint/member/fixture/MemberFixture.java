package com.footprint.member.fixture;

import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;

import com.footprint.member.service.dto.CreateMemberDto;

/**
 * Created by ShinD on 2022/06/26.
 */
public class MemberFixture {
	public static final String USERNAME = "username";
	public static final String PASSWORD = "password";
	public static final String NICKNAME = "nickname";

	public static CreateMemberDto createMemberDto() {
		return new CreateMemberDto(USERNAME, PASSWORD, NICKNAME);
	}

}
