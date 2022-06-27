package com.footprint.follow.controller.dto;

import static com.footprint.follow.fixture.FollowFixture.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class FollowResponseTest {
	@Test
	@DisplayName("FollowDto 에서 FollowResponse 변환 테스트")
	void fromTest() {
		FollowResponse followResponse = FollowResponse.from(getFollowDto());

		assertEquals(getFollowResponse().followId(), followResponse.followId());
		assertEquals(getFollowResponse().followerNickname(), followResponse.followerNickname());
		assertEquals(getFollowResponse().followeeNickname(), followResponse.followeeNickname());
	}
}