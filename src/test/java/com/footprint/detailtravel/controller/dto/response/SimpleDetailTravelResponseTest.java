package com.footprint.detailtravel.controller.dto.response;

import static com.footprint.detailtravel.fixture.DetailTravelFixture.*;
import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.footprint.detailtravel.fixture.DetailTravelFixture;
import com.footprint.detailtravel.service.dto.info.SimpleDetailTravelDto;

/**
 * Created by ShinD on 2022/06/26.
 */
class SimpleDetailTravelResponseTest {

	private final ObjectMapper objectMapper = new ObjectMapper();
	private static final String EXPECT = """
	{
		"detailTravelId":2,
		"mainTravelId":1,
		"title":"[title] title(1)",
		"review":"[review] review(1)",
		"visitedDate":"2022-06-15",
		"address":{"address":"address","roadAddress":"roadAddress","mapX":1,"mapY":2},
		"createdAt":"2022-06-18T00:00"
	}
		""";


	@Test
	@DisplayName("SimpleDetailTravelResponse -> Json 변환 테스트")
	void simpleDetailTravelResponseToJsonTest() throws Exception {
		//given
		SimpleDetailTravelResponse simpleDetailTravelResponse = DetailTravelFixture.simpleDetailTravelResponse(1L, 1);

		//when, then
		assertThat(objectMapper.writeValueAsString(simpleDetailTravelResponse).replace(" ", ""))
			.isEqualTo(EXPECT.trim().replace("\n", "").replace(" ", "").replace("\t", ""));
	}


	@Test
	@DisplayName("SimpleDetailTravelDto -> SimpleDetailTravelResponse 변환 테스트")
	void simpleDetailTravelDtoToSimpleDetailTravelResponseTest() throws Exception {
		//given
		SimpleDetailTravelDto simpleDetailTravelDto = simpleDetailTravelDto(1L, 2);

		//when
		SimpleDetailTravelResponse simpleDetailTravelListResponse = SimpleDetailTravelResponse.from(simpleDetailTravelDto);



		//then
		assertThat(simpleDetailTravelListResponse.detailTravelId()).isEqualTo(simpleDetailTravelDto.detailTravelId());
		assertThat(simpleDetailTravelListResponse.mainTravelId()).isEqualTo(simpleDetailTravelDto.mainTravelId());
		assertThat(simpleDetailTravelListResponse.title()).isEqualTo(simpleDetailTravelDto.title());
		assertThat(simpleDetailTravelListResponse.review()).isEqualTo(simpleDetailTravelDto.review());
		assertThat(simpleDetailTravelListResponse.visitedDate()).isEqualTo(simpleDetailTravelDto.visitedDate().toString());
		assertThat(simpleDetailTravelListResponse.address()).isEqualTo(simpleDetailTravelDto.address());
		assertThat(simpleDetailTravelListResponse.createdAt()).isEqualTo(simpleDetailTravelDto.createdAt().toString());


	}
}