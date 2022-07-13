package com.footprint.comment.service;

import java.util.List;

import com.footprint.comment.service.dto.CommentDto;
import com.footprint.comment.service.dto.CommentSaveDto;
import com.footprint.comment.service.dto.CommentUpdateDto;

public interface CommentService {
	List<CommentDto> getComments(Long travelId);

	Long saveComment(Long travelId, Long memberId, CommentSaveDto commentRequest);

	Long saveCommentReply(Long travelId, Long memberId, Long commentId, CommentSaveDto commentRequest);

	Long updateComment(Long commentId, Long memberId, CommentUpdateDto updateDto);

	void deleteComment(Long commentId, Long memberId);
}
