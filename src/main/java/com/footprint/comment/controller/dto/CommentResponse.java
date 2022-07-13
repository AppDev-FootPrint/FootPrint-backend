package com.footprint.comment.controller.dto;

import java.util.List;

import com.footprint.comment.service.dto.CommentDto;

public record CommentResponse(Long id, String content, String writer, List<CommentResponse> reply) {

	public static CommentResponse from(CommentDto commentDto) {
		return new CommentResponse(commentDto.id(), commentDto.content(), commentDto.writer().getNickname(),
			commentDto.reply().stream().map(CommentResponse::from).toList());
	}
}
