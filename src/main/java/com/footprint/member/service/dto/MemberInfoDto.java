package com.footprint.member.service.dto;

import com.footprint.member.domain.Member;

/**
 * Created by ShinD on 2022/06/23.
 */
public record MemberInfoDto(Long id,
							String username,
							String nickname) {
	public static MemberInfoDto from(Member member) {
		return new MemberInfoDto(member.getId(), member.getUsername(), member.getNickname());
	}
}
