package com.footprint.detailtravel.controller.dto.response;

import static com.footprint.detailtravel.fixture.DetailTravelFixture.*;
import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.footprint.detailtravel.fixture.DetailTravelFixture;
import com.footprint.detailtravel.service.dto.info.SimpleDetailTravelDto;
import com.footprint.detailtravel.service.dto.info.SimpleDetailTravelListDto;

/**
 * Created by ShinD on 2022/06/26.
 */
class SimpleDetailTravelListResponseTest {
	//TODO Price, Image 구현 후 추가하기
	private final ObjectMapper objectMapper = new ObjectMapper();
	private static final String EXPECT = """
	{
		"total":2,
		"detailTravelResponses":[
			{
				"detailTravelId":1,
				"mainTravelId":1,
				"title":"[title] title(0)",
				"review":"[review] review(0)",
				"visitedDate":"2022-06-15",
				"address":{"address":"address","roadAddress":"roadAddress","mapX":1,"mapY":2},
				"createdAt":"2022-06-18T00:00"
			},
			{
				"detailTravelId":2,
				"mainTravelId":1,
				"title":"[title] title(1)",
				"review":"[review] review(1)",
				"visitedDate":"2022-06-15",
				"address":{"address":"address","roadAddress":"roadAddress","mapX":1,"mapY":2},
				"createdAt":"2022-06-18T00:00"
			}
		]
	}
		""";


	@Test
	@DisplayName("SimpleDetailTravelListResponse -> Json 변환 테스트")
	void simpleDetailTravelListResponseToJsonTest() throws Exception {
		//given
		SimpleDetailTravelListResponse simpleDetailTravelListResponse = DetailTravelFixture.simpleDetailTravelListResponse(1L, 2);

		//when, then
		assertThat(objectMapper.writeValueAsString(simpleDetailTravelListResponse).replace(" ", ""))
			.isEqualTo(EXPECT.trim().replace("\n", "").replace(" ", "").replace("\t", ""));
	}


	@Test
	@DisplayName("SimpleDetailTravelListDto -> SimpleDetailTravelListResponse 변환 테스트")
	void simpleDetailTravelListDtoToDetailTravelInfoResponseTest() throws Exception {
		//given
		SimpleDetailTravelListDto simpleDetailTravelListDto = simpleDetailTravelListDto(1L, 2);

		//when
		SimpleDetailTravelListResponse simpleDetailTravelListResponse = SimpleDetailTravelListResponse.from(simpleDetailTravelListDto);



		//then
		assertThat(simpleDetailTravelListResponse.total()).isEqualTo(simpleDetailTravelListDto.total());

		for (int i = 0; i < simpleDetailTravelListResponse.total(); i++) {
			SimpleDetailTravelResponse simpleDetailTravelResponse = simpleDetailTravelListResponse.detailTravelResponses().get(i);
			SimpleDetailTravelDto simpleDetailTravelDto = simpleDetailTravelListDto.detailTravelDtoList().get(i);


			assertThat(simpleDetailTravelResponse.detailTravelId()).isEqualTo(simpleDetailTravelDto.detailTravelId());
			assertThat(simpleDetailTravelResponse.mainTravelId()).isEqualTo(simpleDetailTravelDto.mainTravelId());
			assertThat(simpleDetailTravelResponse.title()).isEqualTo(simpleDetailTravelDto.title());
			assertThat(simpleDetailTravelResponse.review()).isEqualTo(simpleDetailTravelDto.review());
			assertThat(simpleDetailTravelResponse.visitedDate()).isEqualTo(simpleDetailTravelDto.visitedDate().toString());
			assertThat(simpleDetailTravelResponse.address()).isEqualTo(simpleDetailTravelDto.address());
			assertThat(simpleDetailTravelResponse.createdAt()).isEqualTo(simpleDetailTravelDto.createdAt().toString());

		}

	}
}