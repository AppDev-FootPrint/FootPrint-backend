package com.footprint.member.fixture;

import org.springframework.test.util.ReflectionTestUtils;

import com.footprint.member.controller.dto.response.MemberInfoResponse;
import com.footprint.member.domain.Member;
import com.footprint.member.service.dto.CreateMemberDto;
import com.footprint.member.service.dto.MemberInfoDto;

/**
 * Created by ShinD on 2022/06/26.
 */
public class MemberFixture {
	public static final Long MEMBER_ID = 1L;
	public static final String USERNAME = "username";
	public static final String PASSWORD = "password";
	public static final String NICKNAME = "nickname";

	public static CreateMemberDto createMemberDto() {
		return new CreateMemberDto(USERNAME, PASSWORD, NICKNAME);
	}

	public static Member defaultMember() {
		return Member.builder().username(USERNAME).nickname(NICKNAME).password(PASSWORD).build();
	}
	public static Member memberHasId() {
		Member member = Member.builder().username(USERNAME).nickname(NICKNAME).password(PASSWORD).build();
		ReflectionTestUtils.setField(member, "id", MEMBER_ID);
		return member;
	}

	public static Member memberWithUsername(String username) {
		return Member.builder().username(username).nickname(username).password(PASSWORD).build();
	}
	public static MemberInfoDto memberInfoDto() {
		return MemberInfoDto.from(memberHasId());
	}

	public static MemberInfoResponse memberInfoResponse() {
		return MemberInfoResponse.from(memberInfoDto());
	}
}
