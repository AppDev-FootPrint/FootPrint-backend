package com.footprint.follow.controller;

import static com.footprint.follow.fixture.FollowFixture.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.footprint.config.SecurityConfig;
import com.footprint.follow.service.FollowService;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = FollowController.class,
	excludeFilters = {
		@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class)
	}
)
class FollowControllerTest {

	@MockBean
	private FollowService followService;

	private MockMvc mockMvc;
	private final ObjectMapper objectMapper = new ObjectMapper();

	@BeforeEach
	private void setUp(WebApplicationContext webApplicationContext) {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	@Test
	@DisplayName("Follow 성공 테스트")
	void followSuccessTest() throws Exception {
		//given
		given(followService.follow(FOLLOWER_ID, FOLLOWEE_ID)).willReturn(FOLLOW_ID);

		//when
		MvcResult mvcResult = mockMvc.perform(
				post("/api/follows/{followerId}/to/{followeeId}", FOLLOWER_ID, FOLLOWEE_ID)
					.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isCreated())
			.andReturn();

		//then
		assertEquals(String.valueOf(FOLLOW_ID), mvcResult.getResponse().getContentAsString());
	}

	@Test
	@DisplayName("Unfollow 성공 테스트")
	void unfollowSuccessTest() throws Exception {
		//given
		given(followService.existsFollow(FOLLOWER_ID, FOLLOWEE_ID)).willReturn(true);

		//when, then
		mockMvc.perform(delete("/api/follows/{followerId}/to/{followeeId}", FOLLOWER_ID, FOLLOWEE_ID)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andReturn();
	}

	@Test
	@DisplayName("Follower 목록 가져오기 성공 테스트")
	void getFollowerSuccessTest() throws Exception {
		//given
		given(followService.getFollowerByMember(FOLLOWEE_ID)).willReturn(List.of(getFollowDto()));
		String content = objectMapper.writeValueAsString(List.of(getFollowResponse()));

		//when
		MvcResult mvcResult = mockMvc.perform(
				get("/api/members/{memberId}/follower", FOLLOWEE_ID)
					.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andReturn();

		//then
		assertEquals(content, mvcResult.getResponse().getContentAsString());
	}

	@Test
	@DisplayName("Followee 목록 가져오기 성공 테스트")
	void getFolloweeSuccessTest() throws Exception {
		//given
		given(followService.getFolloweeByMember(FOLLOWER_ID)).willReturn(List.of(getFollowDto()));
		String content = objectMapper.writeValueAsString(List.of(getFollowResponse()));

		//when
		MvcResult mvcResult = mockMvc.perform(
				get("/api/members/{memberId}/followee", FOLLOWER_ID)
					.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andReturn();

		//then
		assertEquals(content, mvcResult.getResponse().getContentAsString());
	}
}