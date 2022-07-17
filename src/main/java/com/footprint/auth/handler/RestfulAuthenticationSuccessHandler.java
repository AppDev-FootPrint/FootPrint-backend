package com.footprint.auth.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.footprint.auth.jwt.JwtService;
import com.footprint.member.domain.Member;
import com.footprint.member.exception.MemberException;
import com.footprint.member.exception.MemberExceptionType;
import com.footprint.member.repository.MemberRepository;

/**
 * Created by ShinD on 2022/05/18.
 */
public record RestfulAuthenticationSuccessHandler(JwtService jwtService, MemberRepository memberRepository) implements AuthenticationSuccessHandler {


	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
		jwtService
			.sendToken(response, jwtService.createAccessToken(
				Long.parseLong(((User)authentication.getPrincipal()).getUsername())));//getUsername 에는 회원의 PK, 즉 id 가 들어있음
		Member member = memberRepository.findById(Long.parseLong(((User)authentication.getPrincipal()).getUsername()))
			.orElseThrow(() -> new MemberException(MemberExceptionType.NOT_FOUND));
		response.setContentType("application/json");
		response.setCharacterEncoding("utf-8");

		response.getWriter().write("{ \"nickname\": \"%s\" }".formatted(member.getNickname()));
	}
}
