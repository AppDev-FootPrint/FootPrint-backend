package com.footprint.auth.jwt;

import static com.auth0.jwt.algorithms.Algorithm.*;
import static com.footprint.auth.jwt.JwtToken.*;
import static java.lang.System.*;
import static java.util.Optional.*;
import static java.util.concurrent.TimeUnit.*;

import java.util.Date;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

/**
 * Created by ShinD on 2022/05/18.
 */

@Component
public class JwtServiceImpl implements JwtService {

	public static final String HEADER = "Authorization";

	@Value("${jwt.secret}")
	private String secretKey;

	@Value("${jwt.expirationDay}")
	private long accessTokenValidity;//AccessToken 의 유효기간

	private Algorithm algorithm;

	@PostConstruct
	private void setAlgorithm() {
		algorithm = HMAC512(secretKey);
	}

	@Override
	public JwtToken createAccessToken(Long id) {
		return JwtToken.from(JWT.create()
			.withSubject(ACCESS_TOKEN_SUBJECT) //토큰의 제목
			.withClaim(MEMBER_ID_CLAIM, id)    //토큰의 클레임(정보들)
			.withExpiresAt(new Date(currentTimeMillis() + MILLISECONDS.convert(accessTokenValidity, TimeUnit.DAYS)))
			.sign(algorithm));
	}

	/**
	 * Jwt 에서 회원의 memberId를 추출함
	 */
	@Override
	public Long extractMemberId(JwtToken token) {
		return JWT.require(algorithm).build().verify(token.content()).getClaim(MEMBER_ID_CLAIM).asLong();
	}

	/**
	 * 응답의 Header 에 jwt 설정
	 */
	@Override
	public void sendToken(HttpServletResponse response, JwtToken token) {
		response.setHeader(HEADER, token.content());
	}

	/**
	 * 요청으로부터 포함된 JWT 가 있다면 추출하여 반환
	 */
	@Override
	public Optional<JwtToken> extractToken(HttpServletRequest request) {
		if (request.getHeader(HEADER) == null) {
			return Optional.empty();
		}
		return ofNullable(JwtToken.from(request.getHeader(HEADER).replace("Bearer ", "").trim()));
	}

	/**
	 * JWT 가 유효한지 검사
	 */
	@Override
	public boolean isValid(JwtToken token) {
		return token.isValid(algorithm);
	}
}
