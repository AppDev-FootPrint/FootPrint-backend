package com.footprint.auth;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.footprint.auth.jwt.JwtService;
import com.footprint.auth.jwt.JwtServiceImpl;
import com.footprint.auth.jwt.JwtToken;
import com.footprint.member.domain.Member;
import com.footprint.member.service.MemberService;
import com.footprint.member.service.dto.CreateMemberDto;
/**
 * Created by ShinD on 2022/05/19.
 */
@SpringBootTest
@AutoConfigureMockMvc //https://we1cometomeanings.tistory.com/65
@Transactional
public class AuthTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private MemberService memberService;

	@Autowired
	private JwtService jwtService;

	@Autowired
	private EntityManager em;

	private ObjectMapper objectMapper = new ObjectMapper();

	private static final String LOGIN_URL = "/login";

	private static final String WRONG_USERNAME = "wrong-username";
	private static final String WRONG_PASSWORD = "wrong-password";
	private static final String INVALID_TOKEN = "invalid-token";



	private Map<String, String> signUp(){
		String username = "username";
		String password = "password";
		String nickName = "nickName";

		CreateMemberDto createMemberDto = new CreateMemberDto(username, password, nickName);

		memberService.create(createMemberDto);

		Map<String, String> authMap = new HashMap<>();
		authMap.put("username", username);
		authMap.put("password", password);
		return authMap;
	}



	@Test
	@DisplayName("????????? ??????")
	public void loginTest() throws Exception {
	    //given
		Map<String, String> authMap = signUp();
		objectMapper.writeValueAsString(authMap);

		//when
		MvcResult result = mockMvc.perform(
											post(LOGIN_URL)
											.contentType(MediaType.APPLICATION_JSON)
											.content(objectMapper.writeValueAsString(authMap)))
										.andExpect(status().isOk())
										.andReturn();

	    //then
		Assertions.assertThat(result.getResponse().getHeader(JwtServiceImpl.HEADER)).isNotNull();
		Assertions.assertThat(result.getResponse().getHeader(JwtServiceImpl.HEADER).split("\\.")).hasSize(3);
	}


	@Test
	@DisplayName("????????? ?????? - ????????? ??????")
	public void failLoginByWrongUsername() throws Exception {
		//given
		Map<String, String> authMap = signUp();
		authMap.put("username", WRONG_USERNAME);
		objectMapper.writeValueAsString(authMap);

		//when
		MvcResult result = mockMvc.perform(
											post(LOGIN_URL)
											.contentType(MediaType.APPLICATION_JSON)
											.content(objectMapper.writeValueAsString(authMap)))
									.andExpect(status().isUnauthorized())
									.andReturn();

		//then
		Assertions.assertThat(result.getResponse().getHeader(JwtServiceImpl.HEADER)).isNull();
	}

	@Test
	@DisplayName("????????? ?????? - ???????????? ??????")
	public void failLoginByWrongPassword() throws Exception {
		//given
		Map<String, String> authMap = signUp();
		authMap.put("password", WRONG_PASSWORD);
		objectMapper.writeValueAsString(authMap);

		//when
		MvcResult result = mockMvc.perform(
											post(LOGIN_URL)
											.contentType(MediaType.APPLICATION_JSON)
											.content(objectMapper.writeValueAsString(authMap)))
									.andExpect(status().isUnauthorized())
									.andReturn();

		//then
		Assertions.assertThat(result.getResponse().getHeader(JwtServiceImpl.HEADER)).isNull();
	}


	@Test
	@DisplayName("AccessToken?????? ?????? - ??????")
	public void successAccessByAccessToken() throws Exception {
		//given
		signUp();

		JwtToken accessToken = jwtService.createAccessToken(
			em.createQuery("select m from Member m", Member.class).getSingleResult().getId());


		//when, then
		mockMvc.perform(
				get("/any")
					.header(JwtServiceImpl.HEADER, accessToken.content())
			)
			.andExpect(status().isNotFound());
	}

	@Test
	@DisplayName("AccessToken?????? ?????? - ?????? (?????? : ???????????? ?????? AccessToken)")
	public void failAccessByAccessToken() throws Exception {
		//given
		signUp();

		JwtToken accessToken = jwtService.createAccessToken(
			em.createQuery("select m from Member m", Member.class).getSingleResult().getId());


		mockMvc.perform(
				get("/any")
					.header(JwtServiceImpl.HEADER, INVALID_TOKEN)
			)
			.andExpect(status().isForbidden());
	}


}
