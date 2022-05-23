package com.footprint.maintravel.service.dto;

import static com.footprint.maintravel.fixture.MainTravelFixture.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.footprint.maintravel.domain.MainTravel;

class MainTravelSaveDtoTest {

	@Test
	@DisplayName("SaveDto 에서 Entity 변환 테스트")
	void toEntityTest() throws Exception{
	    //given, when
	    MainTravel mainTravel = getMainTravelSaveDto().toEntity();

	    //then
		assertEquals(getMainTravel().getTitle(), mainTravel.getTitle());
		assertEquals(getMainTravel().getStartDate(), mainTravel.getStartDate());
		assertEquals(getMainTravel().getEndDate(), mainTravel.getEndDate());
		assertEquals(getMainTravel().getIsVisible(), mainTravel.getIsVisible());
		assertEquals(getMainTravel().getIsVisible(), mainTravel.getIsVisible());
	}
}