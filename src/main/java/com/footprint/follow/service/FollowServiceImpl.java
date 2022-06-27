package com.footprint.follow.service;

import static com.footprint.follow.exception.FollowExceptionType.*;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.footprint.follow.domain.Follow;
import com.footprint.follow.exception.FollowException;
import com.footprint.follow.repository.FollowRepository;
import com.footprint.follow.service.dto.FollowDto;
import com.footprint.member.domain.Member;
import com.footprint.member.exception.MemberException;
import com.footprint.member.exception.MemberExceptionType;
import com.footprint.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class FollowServiceImpl implements FollowService {
	private final FollowRepository followRepository;
	private final MemberRepository memberRepository;

	@Override
	public Long follow(Long followerId, Long followeeId) {
		if (existsFollow(followerId, followeeId)) {
			throw new FollowException(CONFLICT);
		}
		Member follower = memberRepository.findById(followerId)
			.orElseThrow(() -> new MemberException(MemberExceptionType.NOT_FOUND));
		Member followee = memberRepository.findById(followeeId)
			.orElseThrow(() -> new MemberException(MemberExceptionType.NOT_FOUND));

		Follow follow = followRepository.save(Follow.builder().follower(follower).followee(followee).build());
		return follow.getId();
	}

	@Override
	public void unfollow(Long followerId, Long followeeId) {
		if (!existsFollow(followerId, followeeId)) {
			throw new FollowException(NOT_FOUND);
		}
		followRepository.deleteFollowByFollowerIdAndFolloweeId(followerId, followeeId);
	}

	@Override
	@Transactional(readOnly = true)
	public boolean existsFollow(Long followerId, Long followeeId) {
		return followRepository.existsFollowByFollowerIdAndFolloweeId(followerId, followeeId);
	}

	@Override
	@Transactional(readOnly = true)
	public List<FollowDto> getFolloweeByMember(Long memberId) {
		List<Follow> followsByFollowerId = followRepository.findAllByFollowerId(memberId);
		return followsByFollowerId.stream().map(FollowDto::from).toList();
	}

	@Override
	@Transactional(readOnly = true)
	public List<FollowDto> getFollowerByMember(Long memberId) {
		List<Follow> followsByFolloweeId = followRepository.findAllByFolloweeId(memberId);
		return followsByFolloweeId.stream().map(FollowDto::from).toList();
	}
}