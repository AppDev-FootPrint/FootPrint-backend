package com.footprint.follow.service.dto;

import static com.footprint.follow.fixture.FollowFixture.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class FollowDtoTest {
	@Test
	@DisplayName("Follow 에서 FollowDto 변환 테스트")
	void fromTest() {
		FollowDto followDto = FollowDto.from(getFollow());

		assertEquals(getFollowDto().followId(), followDto.followId());
		assertEquals(getFollowDto().follower().getId(), followDto.follower().getId());
		assertEquals(getFollowDto().followee().getId(), followDto.followee().getId());
	}
}
