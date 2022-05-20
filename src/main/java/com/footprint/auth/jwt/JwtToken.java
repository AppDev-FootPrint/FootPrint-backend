package com.footprint.auth.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

/**
 * Created by ShinD on 2022/05/18.
 */
public record JwtToken(String content) {

	public static final String ACCESS_TOKEN_SUBJECT = "AccessToken";
	public static final String MEMBER_ID_CLAIM = "memberId";

	public static JwtToken from(String content) {
		return (content == null) ? null : new JwtToken(content);
	}

	public boolean isValid(Algorithm algorithm) {
		try {
			JWT.require(algorithm).build().verify(this.content);
			return true;
		}catch (Exception e){
			return false;
		}
	}
}
