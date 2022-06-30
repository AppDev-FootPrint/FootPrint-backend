package com.footprint.scrap.controller.dto;

import static com.footprint.scrap.fixture.ScrapFixture.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ScrapResponseTest {

	@Test
	@DisplayName("ScrapDto 에서 ScrapResponse 변환 테스트")
	void fromTest() {
		ScrapResponse scrapResponse = ScrapResponse.from(getScrapDto());

		assertEquals(getScrapResponse().scrapId(), scrapResponse.scrapId());
		assertEquals(getScrapResponse().mainTravelListResponse(), scrapResponse.mainTravelListResponse());
	}
}
