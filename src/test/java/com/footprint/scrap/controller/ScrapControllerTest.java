package com.footprint.scrap.controller;

import static com.footprint.maintravel.fixture.MainTravelFixture.*;
import static com.footprint.scrap.fixture.ScrapFixture.*;
import static com.footprint.member.fixture.MemberFixture.*;
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
import com.footprint.scrap.service.ScrapService;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = ScrapController.class,
	excludeFilters = {
		@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class)
	}
)
class ScrapControllerTest {
	@MockBean
	private ScrapService scrapService;

	private MockMvc mockMvc;
	private final ObjectMapper objectMapper = new ObjectMapper();

	private final String SCRAP_SAVE_URL = "/api/members/{memberId}/scraps";
	private final String SCRAP_GET_URL = "/api/members/{memberId}/scraps";
	private final String SCRAP_DELETE_URL = "/api/scraps/{scrapId}";

	@BeforeEach
	private void setUp(WebApplicationContext webApplicationContext) {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	@Test
	@DisplayName("Scrap 성공 테스트")
	void scrapSuccessTest() throws Exception {
		//given
		given(scrapService.saveScrap(MEMBER_ID, MAIN_TRAVEL_ID)).willReturn(SCRAP_ID);

		//when
		MvcResult mvcResult = mockMvc.perform(
				post(SCRAP_SAVE_URL, MEMBER_ID)
					.param("travelId", String.valueOf(MAIN_TRAVEL_ID))
					.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isCreated())
			.andReturn();

		//then
		assertEquals(String.valueOf(SCRAP_ID), mvcResult.getResponse().getContentAsString());
	}

	@Test
	@DisplayName("Scrap 삭제 성공 테스트")
	void deleteScrapSuccessTest() throws Exception {
		MvcResult mvcResult = mockMvc.perform(
				delete(SCRAP_DELETE_URL, SCRAP_ID)
					.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andReturn();
	}

	@Test
	@DisplayName("Scrap 목록 가져오기 성공 테스트")
	void getScrapsSuccessTest() throws Exception {
		//given
		given(scrapService.getScraps(MEMBER_ID)).willReturn(List.of(getScrapDto()));
		String content = objectMapper.writeValueAsString(List.of(getScrapResponse()));

		//when
		MvcResult mvcResult = mockMvc.perform(
				get(SCRAP_GET_URL, MEMBER_ID)
					.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andReturn();

		//then
		assertEquals(content, mvcResult.getResponse().getContentAsString());
	}

}