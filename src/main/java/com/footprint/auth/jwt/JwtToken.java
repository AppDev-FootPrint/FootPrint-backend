package com.footprint.auth.jwt;

/**
 * Created by ShinD on 2022/05/18.
 */
public record JwtToken(String content) {

	public static final String ACCESS_TOKEN_SUBJECT = "AccessToken";
	public static final String MEMBER_ID_CLAIM = "memberId";

	public static JwtToken from(String content) {
		return new JwtToken(content);
	}

}
