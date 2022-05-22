package com.footprint.maintravel.controller.dto;

import static com.footprint.maintravel.fixture.MainTravelFixture.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

class MainTravelResponseTest {
	private final ObjectMapper objectMapper = new ObjectMapper();
	private static final String EXPECT = "{" +
		"\"id\":1," +
		"\"title\":\"title\"," +
		"\"startDate\":\"2022-03-15\"," +
		"\"endDate\":\"2022-03-17\"," +
		"\"isVisible\":true," +
		"\"isCompleted\":true," +
		"\"likeNum\":0" +
		"}";

	@Test
	@DisplayName("mainTravelResponse 변환 테스트")
	void mainTravelResponseTest() throws Exception {
		assertEquals(EXPECT, objectMapper.writeValueAsString(getMainTravelResponse()));
	}

	@Test
	@DisplayName("Service mainTravelDto에서 mainTravelResponse 로 변환 테스트")
	void fromTest() throws Exception {
		//given, when
		MainTravelResponse mainTravelResponse = MainTravelResponse.from(getMainTravelDto());

		//then
		assertEquals(EXPECT, objectMapper.writeValueAsString(mainTravelResponse));
	}
}