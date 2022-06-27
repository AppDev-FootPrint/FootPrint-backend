package com.footprint.follow.fixture;

import org.springframework.test.util.ReflectionTestUtils;

import com.footprint.follow.controller.dto.FollowResponse;
import com.footprint.follow.domain.Follow;
import com.footprint.follow.service.dto.FollowDto;
import com.footprint.member.domain.Member;

public class FollowFixture {
	public static final Long FOLLOWER_ID = 1L;
	public static final String FOLLOWER_USERNAME = "follower";
	public static final String FOLLOWER_PASSWORD = "follower_password";
	public static final String FOLLOWER_NICKNAME = "follower_nickname";

	public static final Long FOLLOWEE_ID = 2L;
	public static final String FOLLOWEE_USERNAME = "followee";
	public static final String FOLLOWEE_PASSWORD = "followee_password";
	public static final String FOLLOWEE_NICKNAME = "followee_nickname";

	public static final Long FOLLOW_ID = 1L;

	public static Member getFollower() {
		Member follower = Member.builder()
			.username(FOLLOWER_USERNAME)
			.password(FOLLOWER_PASSWORD)
			.nickname(FOLLOWER_NICKNAME)
			.build();
		ReflectionTestUtils.setField(follower, "id", FOLLOWEE_ID);
		return follower;
	}

	public static Member getFollowee() {
		Member followee = Member.builder()
			.username(FOLLOWEE_USERNAME)
			.password(FOLLOWEE_PASSWORD)
			.nickname(FOLLOWEE_NICKNAME)
			.build();
		ReflectionTestUtils.setField(followee, "id", FOLLOWEE_ID);
		return followee;
	}

	public static Follow getFollow() {
		Follow follow = Follow.builder().follower(getFollower()).followee(getFollowee()).build();
		ReflectionTestUtils.setField(follow, "id", FOLLOW_ID);
		return follow;
	}

	public static FollowDto getFollowDto() {
		return new FollowDto(FOLLOW_ID, getFollower(), getFollowee());
	}

	public static FollowResponse getFollowResponse() {
		return new FollowResponse(FOLLOW_ID, FOLLOWER_NICKNAME, FOLLOWEE_NICKNAME);
	}
}
