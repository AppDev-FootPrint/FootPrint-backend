package com.footprint.follow.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.footprint.follow.controller.dto.FollowResponse;
import com.footprint.follow.service.FollowService;
import com.footprint.follow.service.dto.FollowDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class FollowController {
	private final FollowService followService;

	@PostMapping("/follows/{followerId}/to/{followeeId}")
	public ResponseEntity<Long> follow(@PathVariable Long followerId, @PathVariable Long followeeId) {
		Long followId = followService.follow(followerId, followeeId);

		URI location = ServletUriComponentsBuilder
			.fromCurrentContextPath().path("/follows/")
			.buildAndExpand(followId).toUri();

		return ResponseEntity.created(location).body(followId);
	}

	@DeleteMapping("/follows/{followerId}/to/{followeeId}")
	public ResponseEntity<Void> unfollow(@PathVariable Long followerId, @PathVariable Long followeeId) {
		followService.unfollow(followerId, followeeId);
		return ResponseEntity.ok().build();
	}

	@GetMapping("/members/{memberId}/follower")
	public ResponseEntity<List<FollowResponse>> getFollower(@PathVariable Long memberId) {
		List<FollowDto> allFollower = followService.getFollowerByMember(memberId);
		return ResponseEntity.ok(allFollower.stream().map(FollowResponse::from).toList());
	}

	@GetMapping("/members/{memberId}/followee")
	public ResponseEntity<List<FollowResponse>> getFollowee(@PathVariable Long memberId) {
		List<FollowDto> allFollowee = followService.getFolloweeByMember(memberId);
		return ResponseEntity.ok(allFollowee.stream().map(FollowResponse::from).toList());
	}
}