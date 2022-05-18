package com.footprint.auth.jwt;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by ShinD on 2022/05/18.
 */
public interface JwtService {
	JwtToken createAccessToken(Long memberId);

	Long extractMemberId(JwtToken token);

	void sendToken(HttpServletResponse response, JwtToken token);

	Optional<String> extractToken(HttpServletRequest request);

	boolean isValid(String jwt);
}
