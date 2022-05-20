package com.footprint.auth.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.footprint.auth.jwt.JwtService;

/**
 * Created by ShinD on 2022/05/18.
 */
public record RestfulAuthenticationSuccessHandler(JwtService jwtService) implements AuthenticationSuccessHandler {

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
		jwtService
			.sendToken(response, jwtService.createAccessToken(
				Long.parseLong(((User)authentication.getPrincipal()).getUsername())));//getUsername 에는 회원의 PK, 즉 id 가 들어있음

	}
}
