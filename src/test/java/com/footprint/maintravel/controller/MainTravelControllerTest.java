package com.footprint.maintravel.controller;

import static com.footprint.maintravel.fixture.MainTravelFixture.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.footprint.maintravel.service.MainTravelService;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = MainTravelController.class)
class MainTravelControllerTest {
	//TODO @RestControllerAdvice 생성 후 실패 테스트 작성

	@MockBean
	private MainTravelService mainTravelService;

	private MockMvc mockMvc;
	private final ObjectMapper objectMapper = new ObjectMapper();

	@BeforeEach
	private void setUp(WebApplicationContext webApplicationContext) {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	@Test
	@DisplayName("MainTravel 단일 조회 성공 테스트")
	void getMainTravelSuccessTest() throws Exception {
		//given
		given(mainTravelService.getMainTravel(MAIN_TRAVEL_ID)).willReturn(getMainTravelDto());
		String content = objectMapper.writeValueAsString(getMainTravelDto());

		//when
		MvcResult result = mockMvc.perform(get("/api/main-travels/{mainTravelId}", MAIN_TRAVEL_ID)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andReturn();

		//then
		assertEquals(content, result.getResponse().getContentAsString());
	}

	@Test
	@DisplayName("MainTravel 저장 성공 테스트")
	void saveMainTravelSuccessTest() throws Exception {
		//given
		given(mainTravelService.saveMainTravel(any())).willReturn(MAIN_TRAVEL_ID);

		//when
		MvcResult result = mockMvc.perform(post("/api/main-travels")
				.content(objectMapper.writeValueAsString(getMainTravelRequest()))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isCreated())
			.andReturn();

		//then
		assertEquals(String.valueOf(MAIN_TRAVEL_ID), result.getResponse().getContentAsString());
	}

	@Test
	@DisplayName("MainTravel 수정 성공 테스트")
	void updateMainTravelSuccessTest() throws Exception {
		//given
		given(mainTravelService.updateMainTravel(any())).willReturn(MAIN_TRAVEL_ID);

		//when
		MvcResult result = mockMvc.perform(put("/api/main-travels/{mainTravelId}", MAIN_TRAVEL_ID)
				.content(objectMapper.writeValueAsString(getMainTravelRequest()))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andReturn();

		//then
		assertEquals(String.valueOf(MAIN_TRAVEL_ID), result.getResponse().getContentAsString());
	}

	@Test
	@DisplayName("MainTravel 삭제 성공 테스트")
	void deleteMainTravelSuccessTest() throws Exception {
		MvcResult result = mockMvc.perform(delete("/api/main-travels/{mainTravelId}", MAIN_TRAVEL_ID)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andReturn();
	}
}