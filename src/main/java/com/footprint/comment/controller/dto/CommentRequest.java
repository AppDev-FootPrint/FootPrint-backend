package com.footprint.comment.controller.dto;

import com.footprint.comment.service.dto.CommentSaveDto;
import com.footprint.comment.service.dto.CommentUpdateDto;

public record CommentRequest(String content) {

	public CommentSaveDto toCommentSaveDto() {
		return new CommentSaveDto(content());
	}

	public CommentUpdateDto toCommentUpdateDto() {
		return new CommentUpdateDto(content());
	}
}
