package com.footprint.follow.service.dto;

import com.footprint.follow.domain.Follow;
import com.footprint.member.domain.Member;

public record FollowDto(Long followId, Member follower, Member followee) {
	public static FollowDto from(Follow follow) {
		return new FollowDto(follow.getId(), follow.getFollower(), follow.getFollowee());
	}
}
