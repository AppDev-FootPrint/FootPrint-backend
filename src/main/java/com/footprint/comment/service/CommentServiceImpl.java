package com.footprint.comment.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.footprint.comment.domain.Comment;
import com.footprint.comment.exception.CommentException;
import com.footprint.comment.exception.CommentExceptionType;
import com.footprint.comment.repository.CommentRepository;
import com.footprint.comment.service.dto.CommentDto;
import com.footprint.comment.service.dto.CommentSaveDto;
import com.footprint.comment.service.dto.CommentUpdateDto;
import com.footprint.maintravel.domain.MainTravel;
import com.footprint.maintravel.exception.MainTravelException;
import com.footprint.maintravel.exception.MainTravelExceptionType;
import com.footprint.maintravel.repository.MainTravelRepository;
import com.footprint.member.domain.Member;
import com.footprint.member.exception.MemberException;
import com.footprint.member.exception.MemberExceptionType;
import com.footprint.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
	private final CommentRepository commentRepository;
	private final MemberRepository memberRepository;
	private final MainTravelRepository mainTravelRepository;

	@Override
	@Transactional(readOnly = true)
	public List<CommentDto> getComments(Long travelId) {
		// List<Comment> comments = commentRepository.findAllByMainTravelIdAndParentId(travelId, null);
		// return comments.stream().map(CommentDto::from).toList();
		List<Comment> comments = commentRepository.findAllWithWriterAndParentByMainTravelId(travelId);
		Map<Long, CommentDto> result = new HashMap<>();
		for (Comment comment : comments) {
			if (comment.getParent() != null)
				continue;
			result.put(comment.getId(), CommentDto.from(comment));
		}
		for (Comment comment : comments) {
			if (comment.getParent() == null)
				continue;
			CommentDto commentDto = result.get(comment.getParent().getId());
			commentDto.reply().add(CommentDto.from(comment));
			result.put(comment.getParent().getId(), commentDto);
		}
		return result.values().stream().toList();
	}

	@Override
	public Long saveComment(Long travelId, Long memberId, CommentSaveDto saveDto) {
		Member writer = memberRepository.findById(memberId)
			.orElseThrow(() -> new MemberException(MemberExceptionType.NOT_FOUND));
		MainTravel mainTravel = mainTravelRepository.findById(travelId)
			.orElseThrow(() -> new MainTravelException(MainTravelExceptionType.NOT_FOUND));
		Comment comment = commentRepository.save(Comment.builder()
			.writer(writer)
			.mainTravel(mainTravel)
			.content(saveDto.content())
			.build());
		return comment.getId();
	}

	@Override
	public Long saveCommentReply(Long travelId, Long memberId, Long commentId, CommentSaveDto saveDto) {
		Member writer = memberRepository.findById(memberId)
			.orElseThrow(() -> new MemberException(MemberExceptionType.NOT_FOUND));
		MainTravel mainTravel = mainTravelRepository.findById(travelId)
			.orElseThrow(() -> new MainTravelException(MainTravelExceptionType.NOT_FOUND));
		Comment parent = commentRepository.findById(commentId)
			.orElseThrow(() -> new CommentException(CommentExceptionType.NOT_FOUND));
		Comment reply = commentRepository.save(Comment.builder()
			.writer(writer)
			.mainTravel(mainTravel)
			.content(saveDto.content())
			.parent(parent)
			.build());
		return reply.getId();
	}

	@Override
	public Long updateComment(Long commentId, Long memberId, CommentUpdateDto updateDto) {
		Comment comment = commentRepository.findById(commentId).orElseThrow();
		if (!comment.getWriter().getId().equals(memberId)) {
			throw new CommentException(CommentExceptionType.NO_AUTHORITY);
		}
		comment.updateComment(updateDto.content());
		return comment.getId();
	}

	@Override
	public void deleteComment(Long commentId, Long memberId) {
		Comment comment = commentRepository.findById(commentId).orElseThrow();
		if (!comment.getWriter().getId().equals(memberId)) {
			throw new CommentException(CommentExceptionType.NO_AUTHORITY);
		}
		commentRepository.delete(comment);
	}
}
