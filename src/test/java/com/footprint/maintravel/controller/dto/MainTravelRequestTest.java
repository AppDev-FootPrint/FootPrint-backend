/*
package com.footprint.maintravel.controller.dto;

import static com.footprint.maintravel.fixture.MainTravelFixture.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.footprint.maintravel.service.dto.save.MainTravelSaveDto;
import com.footprint.maintravel.service.dto.update.MainTravelUpdateDto;

class MainTravelRequestTest {
	private final ObjectMapper objectMapper = new ObjectMapper();
	private static final String REQUEST_BODY = "{\n" +
		"\"isCompleted\":true,\n" +
		"\"title\":\"title\",\n" +
		"\"startDate\":\"2022-03-15\",\n" +
		"\"endDate\":\"2022-03-17\",\n" +
		"\"isVisible\":true\n" +
		"}";

	@Test
	@DisplayName("MainTravelRequest 변환 테스트")
	void mainTravelRequestTest() throws Exception {
		//given, when
		CreateMainTravelRequest mainTravelRequest = objectMapper.readValue(REQUEST_BODY, CreateMainTravelRequest.class);

		//then
		assertEquals(getMainTravelRequest().title(), mainTravelRequest.title());
		assertEquals(getMainTravelRequest().isCompleted(), mainTravelRequest.isCompleted());
		assertEquals(getMainTravelRequest().startDate(), mainTravelRequest.startDate());
		assertEquals(getMainTravelRequest().endDate(), mainTravelRequest.endDate());
		assertEquals(getMainTravelRequest().isVisible(), mainTravelRequest.isVisible());
	}

	@Test
	@DisplayName("MainTravelSaveDto 변환 테스트")
	void toSaveDtoTest() throws Exception {
		//given
		CreateMainTravelRequest mainTravelRequest = objectMapper.readValue(REQUEST_BODY, CreateMainTravelRequest.class);

		//when
		MainTravelSaveDto mainTravelSaveDto = mainTravelRequest.toServiceDto();

		//then
		assertEquals(getMainTravelSaveDto().title(), mainTravelSaveDto.title());
		assertEquals(getMainTravelSaveDto().isCompleted(), mainTravelSaveDto.isCompleted());
		assertEquals(getMainTravelSaveDto().startDate(), mainTravelSaveDto.startDate());
		assertEquals(getMainTravelSaveDto().endDate(), mainTravelSaveDto.endDate());
		assertEquals(getMainTravelSaveDto().isVisible(), mainTravelSaveDto.isVisible());
	}

	@Test
	@DisplayName("MainTravelUpdateDto 변환 테스트")
	void toUpdateDtoTest() throws Exception {
		//given
		CreateMainTravelRequest mainTravelRequest = objectMapper.readValue(REQUEST_BODY, CreateMainTravelRequest.class);

		//when
		MainTravelUpdateDto mainTravelUpdateDto = mainTravelRequest.toUpdateDto(MAIN_TRAVEL_ID);

		//then
		assertEquals(getMainTravelUpdateDto(MAIN_TRAVEL_ID).id(), mainTravelUpdateDto.id());
		assertEquals(getMainTravelUpdateDto(MAIN_TRAVEL_ID).title(), mainTravelUpdateDto.title());
		assertEquals(getMainTravelUpdateDto(MAIN_TRAVEL_ID).isCompleted(), mainTravelUpdateDto.isCompleted());
		assertEquals(getMainTravelUpdateDto(MAIN_TRAVEL_ID).startDate(), mainTravelUpdateDto.startDate());
		assertEquals(getMainTravelUpdateDto(MAIN_TRAVEL_ID).endDate(), mainTravelUpdateDto.endDate());
		assertEquals(getMainTravelUpdateDto(MAIN_TRAVEL_ID).isVisible(), mainTravelUpdateDto.isVisible());
	}
}*/
