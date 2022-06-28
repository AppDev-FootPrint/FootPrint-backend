package com.footprint.follow.controller.dto;

import com.footprint.follow.service.dto.FollowDto;

public record FollowResponse(Long followId, String followerNickname, String followeeNickname) {
	public static FollowResponse from(FollowDto followDto) {
		return new FollowResponse(
			followDto.followId(),
			followDto.follower().getNickname(),
			followDto.followee().getNickname());
	}
}
