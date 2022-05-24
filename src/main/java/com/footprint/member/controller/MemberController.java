package com.footprint.member.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.footprint.member.controller.dto.SignUpRequest;

import com.footprint.member.service.MemberService;

import lombok.RequiredArgsConstructor;

/**
 * Created by ShinD on 2022/05/18.
 */
@RestController
@RequiredArgsConstructor
public class MemberController {

	private final MemberService memberService;

	/**
	 * 회원가입
	 */
	@PostMapping("/signUp")
	public ResponseEntity<Void> signUp(@RequestBody SignUpRequest signUpRequest){
		memberService.create(signUpRequest.toServiceDto());
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}


}
