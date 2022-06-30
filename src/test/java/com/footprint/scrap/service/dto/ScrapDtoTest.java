package com.footprint.scrap.service.dto;

import static com.footprint.scrap.fixture.ScrapFixture.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ScrapDtoTest {
	@Test
	@DisplayName("MainTravel에서 MainTravelDto 변환 테스트")
	void fromTest() {
		// given, when
		ScrapDto scrapDto = ScrapDto.from(getScrap());

		// then
		assertEquals(getScrapDto().scrapId(), scrapDto.scrapId());
		assertEquals(getScrapDto().mainTravelListDto(), scrapDto.mainTravelListDto());
		assertEquals(getScrapDto().mainTravelListDto().title(), scrapDto.mainTravelListDto().title());
		assertEquals(getScrapDto().mainTravelListDto().likeNum(), scrapDto.mainTravelListDto().likeNum());
	}
}
