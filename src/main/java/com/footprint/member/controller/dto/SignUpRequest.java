package com.footprint.member.controller.dto;

import com.footprint.member.service.dto.CreateMemberDto;

/**
 * Created by ShinD on 2022/05/18.
 */
public record SignUpRequest (String username, String password, String nickname) {

	public CreateMemberDto toServiceDto() {
		return CreateMemberDto.builder().username(username).password(password).nickname(nickname).build();
	}
}
