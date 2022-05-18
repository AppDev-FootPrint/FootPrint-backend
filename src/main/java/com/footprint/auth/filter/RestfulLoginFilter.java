package com.footprint.auth.filter;

import static java.util.Objects.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.StreamUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Created by ShinD on 2022/05/17.
 */
public class RestfulLoginFilter extends UsernamePasswordAuthenticationFilter {


	private final ObjectMapper objectMapper;


	public RestfulLoginFilter(AuthenticationManager authenticationManager, ObjectMapper objectMapper) {
		super(authenticationManager);
		this.objectMapper = objectMapper;
	}


	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

		/*
			POST 메서드로 보낸 요청만 처리
		 */
		if (!request.getMethod().equals("POST")) {
			throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
		}

		/*
			Username 과 Password 가 들어있는 HashMap 을 반환
		 */
		HashMap<String, String> authMap = getAuthInfoHashMap(getMessageBody(request));

		UsernamePasswordAuthenticationToken authRequest = makeAuthenticationToken(authMap);
		setDetails(request, authRequest);

		return this.getAuthenticationManager().authenticate(authRequest);
	}




	private UsernamePasswordAuthenticationToken makeAuthenticationToken(HashMap<String, String> authMap) {
		String username = authMap.get(SPRING_SECURITY_FORM_USERNAME_KEY);
		String password = authMap.get(SPRING_SECURITY_FORM_PASSWORD_KEY);
		return new UsernamePasswordAuthenticationToken(username, password);
	}



	private HashMap<String, String> getAuthInfoHashMap(String messageBody) {
		try {

			HashMap<String, String> authInfo = objectMapper.readValue(messageBody, new TypeReference<HashMap<String, String>>() {});

			authInfo.put(SPRING_SECURITY_FORM_USERNAME_KEY, requireNonNull(authInfo.get(SPRING_SECURITY_FORM_USERNAME_KEY)).trim());
			authInfo.put(SPRING_SECURITY_FORM_PASSWORD_KEY, requireNonNull(authInfo.get(SPRING_SECURITY_FORM_PASSWORD_KEY)).trim());

			return authInfo;
		} catch (JsonProcessingException | NullPointerException e) {
			throw new AuthenticationServiceException(e.getMessage());
		}
	}


	private String getMessageBody(HttpServletRequest request) throws AuthenticationException {
		try {
			return StreamUtils.copyToString(request.getInputStream(), StandardCharsets.UTF_8);
		} catch (IOException e) {
			throw new AuthenticationServiceException(e.getMessage());
		}
	}

}
