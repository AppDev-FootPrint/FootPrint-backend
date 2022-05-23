package com.footprint.maintravel.fixture;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.test.util.ReflectionTestUtils;

import com.footprint.maintravel.controller.dto.MainTravelRequest;
import com.footprint.maintravel.controller.dto.MainTravelResponse;
import com.footprint.maintravel.domain.MainTravel;
import com.footprint.maintravel.service.dto.MainTravelDto;
import com.footprint.maintravel.service.dto.MainTravelSaveDto;
import com.footprint.maintravel.service.dto.MainTravelUpdateDto;

public class MainTravelFixture {
	public static final Long MAIN_TRAVEL_ID = 1L;
	public static final String TITLE = "title";
	public static final String UPDATE_TITLE = "update-title";
	public static final String START_DATE_STRING = "2022-03-15";
	public static final LocalDate START_DATE = LocalDate.parse(START_DATE_STRING);
	public static final String END_DATE_STRING = "2022-03-17";
	public static final LocalDate END_DATE = LocalDate.parse(END_DATE_STRING);
	public static final LocalDateTime CREATED_AT = LocalDateTime.of(2022, 3, 18, 10, 10);
	public static final String CREATED_AT_STRING = CREATED_AT.toString();
	public static final Boolean IS_VISIBLE = true;
	public static final Boolean IS_COMPLETED = true;
	public static final int LIKE_NUM = 0;

	public static MainTravel getMainTravel() {
		MainTravel mainTravel = MainTravel.builder()
			.title(TITLE)
			.startDate(START_DATE)
			.endDate(END_DATE)
			.isVisible(IS_VISIBLE)
			.isCompleted(IS_COMPLETED)
			.build();
		ReflectionTestUtils.setField(mainTravel, "createdAt", CREATED_AT);
		return mainTravel;
	}

	public static MainTravelRequest getMainTravelRequest() {
		return new MainTravelRequest(IS_COMPLETED, TITLE, START_DATE_STRING, END_DATE_STRING, IS_VISIBLE);
	}

	public static MainTravelResponse getMainTravelResponse() {
		return new MainTravelResponse(MAIN_TRAVEL_ID, TITLE, START_DATE_STRING, END_DATE_STRING, CREATED_AT_STRING,
			IS_VISIBLE, IS_COMPLETED, LIKE_NUM);
	}

	public static MainTravelDto getMainTravelDto() {
		return new MainTravelDto(MAIN_TRAVEL_ID, TITLE, START_DATE_STRING, END_DATE_STRING, CREATED_AT_STRING,
			IS_VISIBLE, IS_COMPLETED, LIKE_NUM);
	}

	public static MainTravelSaveDto getMainTravelSaveDto() {
		return new MainTravelSaveDto(IS_COMPLETED, TITLE, START_DATE, END_DATE, IS_VISIBLE);
	}

	public static MainTravelUpdateDto getMainTravelUpdateDto() {
		return new MainTravelUpdateDto(MAIN_TRAVEL_ID, IS_COMPLETED, UPDATE_TITLE, START_DATE, END_DATE, IS_VISIBLE);
	}

	public static MainTravelUpdateDto getMainTravelUpdateDto(Long mainTravelId) {
		return getMainTravelRequest().toUpdateDto(mainTravelId);
	}
}
