package com.footprint.auth.jwt;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by ShinD on 2022/05/18.
 */
public interface JwtService {
	JwtToken createAccessToken(Long memberId);//TODO memberId 로만 구성하면 보안상 문제. 이후 배포할 때 바꾸어야 함

	Long extractMemberId(JwtToken token);

	void sendToken(HttpServletResponse response, JwtToken token);

	Optional<JwtToken> extractToken(HttpServletRequest request);

	boolean isValid(JwtToken token);
}
