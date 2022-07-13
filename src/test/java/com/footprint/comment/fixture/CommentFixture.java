package com.footprint.comment.fixture;

import static com.footprint.member.fixture.MemberFixture.*;
import static com.footprint.maintravel.fixture.MainTravelFixture.*;

import java.util.List;

import org.springframework.test.util.ReflectionTestUtils;

import com.footprint.comment.controller.dto.CommentRequest;
import com.footprint.comment.controller.dto.CommentResponse;
import com.footprint.comment.domain.Comment;
import com.footprint.comment.service.dto.CommentDto;
import com.footprint.comment.service.dto.CommentSaveDto;
import com.footprint.comment.service.dto.CommentUpdateDto;

public class CommentFixture {
	public static final Long COMMENT_ID = 1L;
	public static final String COMMENT_CONTENT = "comment";
	public static final String COMMENT_UPDATE_CONTENT = "update comment";

	public static final Long REPLY_ID = 2L;
	public static final String REPLY_CONTENT = "reply";

	public static Comment comment() {
		Comment comment = Comment.builder()
			.content(COMMENT_CONTENT)
			.writer(defaultMember())
			.mainTravel(mainTravelHasId())
			.build();

		ReflectionTestUtils.setField(comment, "id", COMMENT_ID);

		return comment;
	}

	public static Comment reply() {
		Comment reply = Comment.builder()
			.content(REPLY_CONTENT)
			.writer(defaultMember())
			.mainTravel(mainTravelHasId())
			.parent(comment())
			.build();
		ReflectionTestUtils.setField(reply, "id", REPLY_ID);
		return reply;
	}

	public static CommentDto commentDto() {
		return new CommentDto(COMMENT_ID, COMMENT_CONTENT, defaultMember(),
			List.of(new CommentDto(REPLY_ID, REPLY_CONTENT, defaultMember(), List.of())));
	}

	public static CommentSaveDto commentSaveDto() {
		return new CommentSaveDto(COMMENT_CONTENT);
	}

	public static CommentUpdateDto commentUpdateDto() {
		return new CommentUpdateDto(COMMENT_UPDATE_CONTENT);
	}

	public static CommentRequest commentSaveRequest() {
		return new CommentRequest(COMMENT_CONTENT);
	}

	public static CommentRequest commentUpdateRequest() {
		return new CommentRequest(COMMENT_UPDATE_CONTENT);
	}

	public static CommentResponse commentResponse() {
		return new CommentResponse(COMMENT_ID, COMMENT_CONTENT, NICKNAME,
			List.of(new CommentResponse(REPLY_ID, REPLY_CONTENT, NICKNAME, List.of())));
	}
}
