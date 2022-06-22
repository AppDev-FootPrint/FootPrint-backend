package com.footprint.follow.service;

import static com.footprint.follow.fixture.FollowFixture.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import com.footprint.follow.exception.FollowException;
import com.footprint.follow.repository.FollowRepository;
import com.footprint.follow.service.dto.FollowDto;
import com.footprint.member.exception.MemberException;
import com.footprint.member.repository.MemberRepository;

@ExtendWith(MockitoExtension.class)
class FollowServiceTest {
	private final FollowRepository followRepository = mock(FollowRepository.class);
	private final MemberRepository memberRepository = mock(MemberRepository.class);

	@InjectMocks
	private final FollowService followService = new FollowServiceImpl(followRepository, memberRepository);

	@Test
	@DisplayName("Follow 성공")
	void followSuccessTest() {
		//given
		given(memberRepository.findById(FOLLOWER_ID)).willReturn(Optional.of(getFollower()));
		given(memberRepository.findById(FOLLOWEE_ID)).willReturn(Optional.of(getFollowee()));
		given(followRepository.existsFollowByFollowerIdAndFolloweeId(FOLLOWER_ID, FOLLOWEE_ID)).willReturn(false);
		given(followRepository.save(any())).willReturn(getFollow());

		//when
		Long followId = followService.follow(FOLLOWER_ID, FOLLOWEE_ID);

		//then
		assertEquals(FOLLOW_ID, followId);
	}

	@Test
	@DisplayName("Follow 실패 - 이미 Follow 인 경우")
	void followFailTestToAlreadyFollow() {
		//given
		given(followRepository.existsFollowByFollowerIdAndFolloweeId(FOLLOWER_ID, FOLLOWEE_ID)).willReturn(true);

		//when, then
		assertThrows(FollowException.class, () -> followService.follow(FOLLOWER_ID, FOLLOWEE_ID));
	}

	@Test
	@DisplayName("Follow 실패 - 해당 유저가 존재하지 않는 경우")
	void followFailTestToNotFoundUser() {
		//given
		given(memberRepository.findById(FOLLOWER_ID)).willReturn(Optional.empty());
		given(memberRepository.findById(FOLLOWEE_ID)).willReturn(Optional.of(getFollowee()));
		given(followRepository.existsFollowByFollowerIdAndFolloweeId(FOLLOWER_ID, FOLLOWEE_ID)).willReturn(false);

		//when, then
		assertThrows(MemberException.class, () -> followService.follow(FOLLOWER_ID, FOLLOWEE_ID));
	}

	@Test
	@DisplayName("Unfollow 성공")
	void unfollowSuccessTest() {
		//given
		given(followRepository.existsFollowByFollowerIdAndFolloweeId(FOLLOWER_ID, FOLLOWEE_ID)).willReturn(true);

		//when
		followService.unfollow(FOLLOWER_ID, FOLLOWEE_ID);

		// then
		verify(followRepository, times(1)).deleteFollowByFollowerIdAndFolloweeId(FOLLOWER_ID, FOLLOWEE_ID);
	}

	@Test
	@DisplayName("Unfollow 실패")
	void unfollowFailTest() {
		//given
		given(followRepository.existsFollowByFollowerIdAndFolloweeId(FOLLOWER_ID, FOLLOWEE_ID)).willReturn(false);

		//when, then
		assertThrows(FollowException.class, () -> followService.unfollow(FOLLOWER_ID, FOLLOWEE_ID));
	}

	@Test
	@DisplayName("Follower List 가져오기")
	void getFollowerListByFolloweeTest() {
		// given
		given(followRepository.findAllByFolloweeId(FOLLOWEE_ID)).willReturn(List.of(getFollow()));

		// when
		List<FollowDto> allByFollowee = followService.getFollowerByMember(FOLLOWEE_ID);

		// then
		assertEquals(1, allByFollowee.size());
	}

	@Test
	@DisplayName("Followee List 가져오기")
	void getFolloweeListByFollowerTest() {
		// given
		given(followRepository.findAllByFollowerId(FOLLOWER_ID)).willReturn(List.of(getFollow()));

		// when
		List<FollowDto> allByFollower = followService.getFolloweeByMember(FOLLOWER_ID);

		// then
		assertEquals(1, allByFollower.size());
	}
}