package com.footprint.auth.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import com.footprint.auth.jwt.JwtService;
import com.footprint.member.domain.Member;
import com.footprint.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

/**
 * Created by ShinD on 2022/05/18.
 */
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {//TODO Filter 를 상속받으면 두번 작동함

	private final JwtService jwtService;
	private final MemberRepository memberRepository;

	private final List<String> NO_CHECK_URL = Arrays.asList("/login", "/signUp");


	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

		/*
			요청 정보로부터 인증을 시도함. 인증 시 SecurityContextHolder 에 인증정보 저장
		 */
		executeAuthentication((HttpServletRequest)request);

		if(SecurityContextHolder.getContext().getAuthentication() == null) {
			filterChain.doFilter(request, response);
		}
	}

	private void executeAuthentication(HttpServletRequest request) {
		jwtService.extractToken(request)
			      .filter(jwtService::isValid)
			      .map(jwtService::extractMemberId)
			      .flatMap(memberRepository::findById)
			      .ifPresent(this::saveSecurityContextHolder);
	}




	private void saveSecurityContextHolder(Member member) {
		SecurityContext context = SecurityContextHolder.createEmptyContext();
		UserDetails userDetails = User.builder()
			.username(member.getId().toString())//UserDetails 의 username 에 id 값을 넣어줌
			.password(member.getPassword())//이거 안해주면 오류남!
			.authorities(new ArrayList<>()).build();

		context.setAuthentication(new UsernamePasswordAuthenticationToken(userDetails, null));
		SecurityContextHolder.setContext(context);
	}
}
