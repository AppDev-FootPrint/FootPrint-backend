package com.footprint.detailtravel.controller;

import static com.footprint.detailtravel.fixture.DetailTravelFixture.*;
import static com.footprint.maintravel.fixture.MainTravelFixture.*;
import static com.footprint.member.fixture.MemberFixture.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.footprint.auth.jwt.JwtService;
import com.footprint.auth.service.AuthService;
import com.footprint.detailtravel.service.DetailTravelService;
import com.footprint.member.repository.MemberRepository;

/**
 * Created by ShinD on 2022/06/29.
 */
@WebMvcTest(DetailTravelController.class)
class DetailTravelControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private DetailTravelService detailTravelService;

	@MockBean
	AuthService authService;

	@MockBean
	private JwtService jwtService;

	@MockBean
	private MemberRepository memberRepository;

	private final ObjectMapper objectMapper = new ObjectMapper();

	private static final String DETAIL_TRAVEL_GET_URL = "/api/detail-travels/{id}";




	@BeforeEach
	private void setUp() {
		given(authService.getLoginMemberId()).willReturn(MEMBER_ID);
	}



	@Test
	@DisplayName("DetailTravel 단일 조회 성공 테스트")
	@WithMockUser
	void getMainTravelSuccessTest() throws Exception {
		//given
		given(detailTravelService.getById(MEMBER_ID, DETAIL_TRAVEL_ID)).willReturn(detailTravelDto());

		String content = objectMapper.writeValueAsString(detailTravelInfoResponse());

		//when
		MvcResult result = mockMvc.perform(get(DETAIL_TRAVEL_GET_URL, DETAIL_TRAVEL_ID)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andReturn();

		//then
		assertEquals(content, result.getResponse().getContentAsString());
	}

}