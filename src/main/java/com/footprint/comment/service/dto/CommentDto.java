package com.footprint.comment.service.dto;

import java.util.ArrayList;
import java.util.List;

import com.footprint.comment.domain.Comment;
import com.footprint.member.domain.Member;

public record CommentDto(Long id, String content, Member writer, List<CommentDto> reply) {

	public static CommentDto from(Comment comment) {
		return new CommentDto(comment.getId(), comment.getContent(), comment.getWriter(),
			// comment.getChildren().stream().map(CommentDto::from).toList());
			new ArrayList<>());
	}
}
