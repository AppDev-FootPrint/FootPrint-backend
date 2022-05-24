package com.footprint.maintravel.service.dto;

import static com.footprint.maintravel.fixture.MainTravelFixture.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MainTravelDtoTest {

	@Test
	@DisplayName("MainTravel에서 MainTravelDto 변환 테스트")
	void fromTest() throws Exception {
		// given, when
		MainTravelDto mainTravelDto = MainTravelDto.from(getMainTravel());

		// then
		assertEquals(getMainTravelDto().title(), mainTravelDto.title());
		assertEquals(getMainTravelDto().startDate(), mainTravelDto.startDate());
		assertEquals(getMainTravelDto().endDate(), mainTravelDto.endDate());
		assertEquals(getMainTravelDto().isVisible(), mainTravelDto.isVisible());
		assertEquals(getMainTravelDto().isCompleted(), mainTravelDto.isCompleted());
	}

}