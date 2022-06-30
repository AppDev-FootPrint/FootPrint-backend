package com.footprint.scrap.fixture;

import static com.footprint.member.fixture.MemberFixture.*;
import static com.footprint.maintravel.fixture.MainTravelFixture.*;

import org.springframework.test.util.ReflectionTestUtils;

import com.footprint.scrap.controller.dto.ScrapResponse;
import com.footprint.scrap.domain.Scrap;
import com.footprint.scrap.service.dto.ScrapDto;

public class ScrapFixture {
	public static final Long SCRAP_ID = 1L;

	public static Scrap getScrap() {
		Scrap scrap = Scrap.builder().member(defaultMember()).mainTravel(mainTravelHasId()).build();
		ReflectionTestUtils.setField(scrap, "id", SCRAP_ID);
		return scrap;
	}

	public static ScrapDto getScrapDto() {
		return new ScrapDto(SCRAP_ID, mainTravelListDto());
	}

	public static ScrapResponse getScrapResponse() {
		return new ScrapResponse(SCRAP_ID, mainTravelListResponse());
	}
}
