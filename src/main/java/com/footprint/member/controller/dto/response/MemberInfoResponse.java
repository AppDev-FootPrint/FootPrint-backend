package com.footprint.member.controller.dto.response;

import com.footprint.member.service.dto.MemberInfoDto;

/**
 * Created by ShinD on 2022/06/25.
 */
public record MemberInfoResponse(Long id,
								 String username,
								 String nickname) {

	public static MemberInfoResponse from(MemberInfoDto memberInfoDto) {
		return new MemberInfoResponse(memberInfoDto.id(), memberInfoDto.username(), memberInfoDto.nickname());
	}
}
