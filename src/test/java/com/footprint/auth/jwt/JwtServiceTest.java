package com.footprint.auth.jwt;

import static com.auth0.jwt.algorithms.Algorithm.*;
import static com.footprint.auth.jwt.JwtServiceImpl.*;
import static java.lang.System.*;
import static java.util.concurrent.TimeUnit.*;
import static org.assertj.core.api.Assertions.*;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;

/**
 * Created by ShinD on 2022/05/18.
 */
@SpringBootTest
class JwtServiceTest {

	@Autowired
	private JwtService jwtService;

	@Value("${jwt.secret}")
	private String secret;

	@Value("${jwt.expirationDay}")
	private long accessTokenValidity;//AccessToken 의 유효기간




	private DecodedJWT getVerify(String token) {
		return JWT.require(HMAC512(secret)).build().verify(token);
	}

	private HttpServletRequest setRequest(JwtToken accessToken) {
		MockHttpServletRequest httpServletRequest = new MockHttpServletRequest();

		httpServletRequest.addHeader(HEADER, accessToken.content());

		return httpServletRequest;
	}


	@Test
	@DisplayName("AccessToken 발급 테스트 - 성공")
	public void successCreateAccessToken() throws Exception {
	    //given
		Long memberId = 1L;

		//when
		JwtToken accessToken = jwtService.createAccessToken(memberId);

	    //then
		DecodedJWT verify = getVerify(accessToken.content());
		assertThat(verify.getClaim(JwtToken.MEMBER_ID_CLAIM).asLong()).isEqualTo(memberId);
		assertThat(
				verify.getExpiresAt().before(new Date(
					currentTimeMillis() + MILLISECONDS.convert(accessTokenValidity, TimeUnit.DAYS))
				)).isTrue();
		assertThat(
			verify.getExpiresAt().after(new Date(
				currentTimeMillis() + MILLISECONDS.convert(accessTokenValidity, TimeUnit.DAYS) - + MILLISECONDS.convert(1, HOURS))
			)).isTrue();
	}


	@Test
	@DisplayName("AccessToken 에서 memberId 추출 - 성공")
	public void successExtractMemberId() throws Exception {
		//given
		Long memberId = 1L;
		JwtToken accessToken = jwtService.createAccessToken(memberId);

		//when
		Long extractMemberId = jwtService.extractMemberId(accessToken);

		//then
		assertThat(extractMemberId).isEqualTo(memberId);
	}





	@Test
	@DisplayName("토큰 유효성 검사 - 성공")
	public void successTokenValidity() throws Exception {
		//given
		Long memberId = 1L;
		JwtToken accessToken = jwtService.createAccessToken(memberId);

		//when, then
		assertThat(jwtService.isValid(accessToken)).isTrue();
	}



	@Test
	@DisplayName("AccessToken 응답으로 전송 - 성공")
	public void successSendAccessAndRefreshToken() throws Exception {
		//given
		MockHttpServletResponse mockHttpServletResponse = new MockHttpServletResponse();

		Long memberId = 1L;
		JwtToken accessToken = jwtService.createAccessToken(memberId);


		//when
		jwtService.sendToken(mockHttpServletResponse, accessToken);


		//then
		String headerAccessToken = mockHttpServletResponse.getHeader(HEADER);



		assertThat(headerAccessToken).isEqualTo(accessToken.content());
	}


	@Test
	@DisplayName("요청으로부터 AccessToken 추출 - 성공")
	public void successExtractAccessToken() throws Exception {
		//given
		Long memberId = 1L;
		JwtToken accessToken = jwtService.createAccessToken(memberId);
		HttpServletRequest httpServletRequest = setRequest(accessToken);

		//when
		JwtToken extractAccessToken = jwtService.extractToken(httpServletRequest).orElseThrow(()-> new Exception("토큰이 없습니다"));


		//then
		assertThat(getVerify(extractAccessToken.content()).getClaim(JwtToken.MEMBER_ID_CLAIM).asLong()).isEqualTo(memberId);
	}



	@Test
	@DisplayName("request -> 토큰 -> memberId 추출 - 성공")
	public void successRequestToExtractMemberId() throws Exception {
		//given
		Long memberId = 1L;
		JwtToken accessToken = jwtService.createAccessToken(memberId);
		HttpServletRequest httpServletRequest = setRequest(accessToken);

		//when
		JwtToken extractAccessToken = jwtService.extractToken(httpServletRequest).orElseThrow(()-> new Exception("토큰이 없습니다"));


		//then
		assertThat(getVerify(extractAccessToken.content()).getClaim(JwtToken.MEMBER_ID_CLAIM).asLong()).isEqualTo(memberId);
	}

}