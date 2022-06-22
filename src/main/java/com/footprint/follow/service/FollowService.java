package com.footprint.follow.service;

import java.util.List;

import com.footprint.follow.service.dto.FollowDto;

public interface FollowService {

	Long follow(Long followerId, Long followeeId);

	void unfollow(Long followerId, Long followeeId);

	boolean existsFollow(Long followerId, Long followeeId);

	List<FollowDto> getFolloweeByMember(Long memberId);

	List<FollowDto> getFollowerByMember(Long memberId);
}
