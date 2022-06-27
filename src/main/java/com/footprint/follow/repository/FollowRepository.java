package com.footprint.follow.repository;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.footprint.follow.domain.Follow;

public interface FollowRepository extends JpaRepository<Follow, Long> {

	boolean existsFollowByFollowerIdAndFolloweeId(Long followerId, Long followeeId);

	void deleteFollowByFollowerIdAndFolloweeId(Long followerId, Long followeeId);

	@EntityGraph(attributePaths = {"followee", "follower"})
	List<Follow> findAllByFolloweeId(Long followeeId);

	@EntityGraph(attributePaths = {"follower", "followee"})
	List<Follow> findAllByFollowerId(Long followerId);
}