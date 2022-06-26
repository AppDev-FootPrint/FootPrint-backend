package com.footprint.member.fixture;

import com.footprint.member.domain.Member;
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

	public static Member defaultMember() {
		return Member.builder().username(USERNAME).nickname(NICKNAME).password(PASSWORD).build();
	}

	public static Member memberWithUsername(String username) {
		return Member.builder().username(username).nickname(username).password(PASSWORD).build();
	}
}
