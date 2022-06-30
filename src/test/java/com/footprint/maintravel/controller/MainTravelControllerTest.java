package com.footprint.maintravel.controller;

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
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.footprint.auth.jwt.JwtService;
import com.footprint.auth.service.AuthService;
import com.footprint.maintravel.service.MainTravelService;
import com.footprint.member.repository.MemberRepository;


@WebMvcTest(controllers = MainTravelController.class)
class MainTravelControllerTest {
	//TODO @RestControllerAdvice 생성 후 실패 테스트 작성


	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private MainTravelService mainTravelService;

	@MockBean
	AuthService authService;

	@MockBean
	private JwtService jwtService;

	@MockBean
	private MemberRepository memberRepository;

	private final ObjectMapper objectMapper = new ObjectMapper();

	private static final String MAIN_TRAVEL_SAVE_URL = "/api/main-travels";
	private static final String MAIN_TRAVEL_UPDATE_URL = "/api/main-travels/{id}";
	private static final String MAIN_TRAVEL_DELETE_URL = "/api/main-travels/{id}";
	private static final String MAIN_TRAVEL_GET_URL = "/api/main-travels/{id}";


	@BeforeEach
	private void setUp() {
		given(authService.getLoginMemberId()).willReturn(MEMBER_ID);
	}



	@Test
	@DisplayName("MainTravel 단일 조회 성공 테스트")
	@WithMockUser
	void getMainTravelSuccessTest() throws Exception {
		//given
		given(mainTravelService.getMainTravel(MEMBER_ID, MAIN_TRAVEL_ID)).willReturn(mainTravelInfoDto());

		String content = objectMapper.writeValueAsString(mainTravelInfoResponse());

		//when
		MvcResult result = mockMvc.perform(get(MAIN_TRAVEL_GET_URL, MAIN_TRAVEL_ID)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andReturn();

		//then
		assertEquals(content, result.getResponse().getContentAsString());
	}

	@Test
	@DisplayName("MainTravel 저장 성공 테스트")
	@WithMockUser
	void saveMainTravelSuccessTest() throws Exception {
		//given
		given(mainTravelService.saveMainTravel(MEMBER_ID, createMainTravelRequest().toServiceDto())).willReturn(MAIN_TRAVEL_ID);

		//when
		MvcResult result = mockMvc.perform(post(MAIN_TRAVEL_SAVE_URL)
				.content(objectMapper.writeValueAsString(createMainTravelRequest()))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isCreated())
			.andReturn();

		//then
		assertEquals(String.valueOf(MAIN_TRAVEL_ID), result.getResponse().getContentAsString());
	}

	@Test
	@DisplayName("MainTravel 수정 성공 테스트")
	@WithMockUser
	void updateMainTravelSuccessTest() throws Exception {
		//given
		given(mainTravelService.updateMainTravel(MEMBER_ID, updateMainTravelRequest().toServiceDto(MAIN_TRAVEL_ID))).willReturn(MAIN_TRAVEL_ID);

		//when
		MvcResult result = mockMvc.perform(put(MAIN_TRAVEL_UPDATE_URL, MAIN_TRAVEL_ID)
				.content(objectMapper.writeValueAsString(updateMainTravelRequest()))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andReturn();

		//then
		assertEquals(String.valueOf(MAIN_TRAVEL_ID), result.getResponse().getContentAsString());
	}

	@Test
	@DisplayName("MainTravel 삭제 성공 테스트")
	@WithMockUser
	void deleteMainTravelSuccessTest() throws Exception {
		MvcResult result = mockMvc.perform(delete(MAIN_TRAVEL_DELETE_URL, MAIN_TRAVEL_ID)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andReturn();
	}
}
