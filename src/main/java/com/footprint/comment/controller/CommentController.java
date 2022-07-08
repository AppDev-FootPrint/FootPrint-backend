package com.footprint.comment.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.footprint.auth.service.AuthService;
import com.footprint.comment.controller.dto.CommentRequest;
import com.footprint.comment.controller.dto.CommentResponse;
import com.footprint.comment.service.CommentService;
import com.footprint.comment.service.dto.CommentDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CommentController {
	private final CommentService commentService;
	private final AuthService authService;

	@GetMapping("/main-travels/{travelId}/comments")
	public ResponseEntity<List<CommentResponse>> getComments(@PathVariable Long travelId) {
		List<CommentDto> comments = commentService.getComments(travelId);
		return ResponseEntity.ok(comments.stream().map(CommentResponse::from).toList());
	}

	@PostMapping("/main-travels/{travelId}/comments")
	public ResponseEntity<Long> saveComment(@PathVariable Long travelId, @RequestBody CommentRequest commentRequest) {
		Long memberId = authService.getLoginMemberId();
		Long commentId = commentService.saveComment(travelId, memberId, commentRequest.toCommentSaveDto());

		URI location = ServletUriComponentsBuilder
			.fromCurrentContextPath().path("/comments/")
			.buildAndExpand(commentId).toUri();

		return ResponseEntity.created(location).body(commentId);
	}

	@PostMapping("/main-travels/{travelId}/comments/{commentId}")
	public ResponseEntity<Long> saveCommentReply(@PathVariable Long travelId, @PathVariable Long commentId,
		@RequestBody CommentRequest commentRequest) {
		Long memberId = authService.getLoginMemberId();
		Long commentReplyId = commentService.saveCommentReply(travelId, memberId, commentId,
			commentRequest.toCommentSaveDto());

		URI location = ServletUriComponentsBuilder
			.fromCurrentContextPath().path("/comments/")
			.buildAndExpand(commentReplyId).toUri();

		return ResponseEntity.created(location).body(commentReplyId);
	}

	@PutMapping("/comments/{commentId}")
	public ResponseEntity<Long> deleteComment(@PathVariable Long commentId, @RequestBody CommentRequest commentRequest) {
		Long memberId = authService.getLoginMemberId();
		Long updateCommentId = commentService.updateComment(commentId, memberId, commentRequest.toCommentUpdateDto());
		return ResponseEntity.ok(updateCommentId);
	}

	@DeleteMapping("/comments/{commentId}")
	public ResponseEntity<Void> deleteComment(@PathVariable Long commentId) {
		Long memberId = authService.getLoginMemberId();
		commentService.deleteComment(commentId, memberId);
		return ResponseEntity.ok().build();
	}
}
