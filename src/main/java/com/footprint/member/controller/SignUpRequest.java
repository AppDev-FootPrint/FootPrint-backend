package com.footprint.member.controller;

import com.footprint.member.service.dto.CreateMemberDto;

/**
 * Created by ShinD on 2022/05/18.
 */
public record SignUpRequest (String username, String password, String nickName) {

	public CreateMemberDto toServiceDto() {
		return CreateMemberDto.builder().username(username).password(password).nickName(nickName).build();
	}
}