package com.footprint.comment.service.dto;

import static com.footprint.comment.fixture.CommentFixture.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CommentDtoTest {
	@Test
	@DisplayName("Follow 에서 FollowDto 변환 테스트")
	void fromTest() {
		CommentDto commentDto = CommentDto.from(comment());

		assertEquals(commentDto().id(), commentDto.id());
		assertEquals(commentDto().content(), commentDto.content());
		assertEquals(commentDto().writer().getId(), commentDto.writer().getId());
	}
}