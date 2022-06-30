package com.footprint.maintravel.controller.dto.response;

import static com.footprint.maintravel.fixture.MainTravelFixture.*;
import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.footprint.detailtravel.controller.dto.response.SimpleDetailTravelResponse;
import com.footprint.detailtravel.service.dto.info.SimpleDetailTravelDto;
import com.footprint.maintravel.service.dto.info.MainTravelInfoDto;

/**
 * Created by ShinD on 2022/06/26.
 */
class MainTravelInfoResponseTest {

	private final ObjectMapper objectMapper = new ObjectMapper();
	private static final String EXPECT = """
	{ 
		"id" : 1 ,
		"writerInfo" : {"id":1,"username":"username","nickname":"nickname"},
		"title":"title",
		"startDate":"2022-06-15",
		"endDate":"2022-06-17",
		"createdAt":"2022-06-22T00:00",
		"isVisible":true,
		"isCompleted":true,
		"mainImagePath":"https://ttl-blog.tistory.com/",
		"likeNum":0,
		"simpleDetailTravelListResponse":
		{
			"total":3,
			"detailTravelResponses":
			[
				{
					"detailTravelId": 1,
					"mainTravelId":1,
					"title":"[title] title (0)",
					"review":"[review] review (0)",
					"visitedDate":"2022-06-15",
					"address":{"address":"address","roadAddress":"roadAddress","mapX":1,"mapY":2},
					"createdAt":"2022-06-18T00:00"
				},
				{
					"detailTravelId":2,
					"mainTravelId":1,
					"title":"[title] title (1)",
					"review":"[review] review (1)",
					"visitedDate":"2022-06-15",
					"address": { "address":"address","roadAddress":"roadAddress","mapX":1,"mapY":2},
					"createdAt":"2022-06-18T00:00"
				},
				{
					"detailTravelId":3,
					"mainTravelId":1,
					"title":"[title] title (2)",
					"review":"[review] review (2)",
					"visitedDate":"2022-06-15",
					"address":{"address":"address","roadAddress":"roadAddress","mapX":1,"mapY":2},
					"createdAt":"2022-06-18T00:00"
				}
			]
		}
	}
		""";


	@Test
	@DisplayName("MainTravelInfoResponse -> Json 변환 테스트")
	void mainTravelInfoResponseToJsonTest() throws Exception {
		//given, when
		MainTravelInfoResponse mainTravelInfoResponse = mainTravelInfoResponse();
		assertThat(objectMapper.writeValueAsString(mainTravelInfoResponse).replace(" ", ""))
			.isEqualTo(EXPECT.trim().replace("\n", "").replace(" ", "").replace("\t", ""));
	}

	@Test
	@DisplayName("MainTravelInfoDto -> MainTravelInfoResponse 변환 테스트")
	void mainTravelInfoDtoToMainTravelInfoResponse() throws Exception {
		//given, when
		MainTravelInfoDto mainTravelInfoDto = mainTravelInfoDto();
		MainTravelInfoResponse mainTravelInfoResponse = MainTravelInfoResponse.from(mainTravelInfoDto);

		assertThat(mainTravelInfoResponse.id()).isEqualTo(mainTravelInfoDto.id());
		assertThat(mainTravelInfoResponse.writerInfo().id()).isEqualTo(mainTravelInfoDto.writerInfo().id());
		assertThat(mainTravelInfoResponse.writerInfo().nickname()).isEqualTo(mainTravelInfoDto.writerInfo().nickname());
		assertThat(mainTravelInfoResponse.writerInfo().username()).isEqualTo(mainTravelInfoDto.writerInfo().username());
		assertThat(mainTravelInfoResponse.title()).isEqualTo(mainTravelInfoDto.title());
		assertThat(mainTravelInfoResponse.startDate()).isEqualTo(mainTravelInfoDto.startDate());
		assertThat(mainTravelInfoResponse.endDate()).isEqualTo(mainTravelInfoDto.endDate());
		assertThat(mainTravelInfoResponse.createdAt()).isEqualTo(mainTravelInfoDto.createdAt());
		assertThat(mainTravelInfoResponse.isVisible()).isEqualTo(mainTravelInfoDto.isVisible());
		assertThat(mainTravelInfoResponse.isCompleted()).isEqualTo(mainTravelInfoDto.isCompleted());
		assertThat(mainTravelInfoResponse.mainImagePath()).isEqualTo(mainTravelInfoDto.mainImagePath());
		assertThat(mainTravelInfoResponse.likeNum()).isEqualTo(mainTravelInfoDto.likeNum());

		for (int i = 0; i < mainTravelInfoDto.simpleDetailTravelListDto().total(); i++) {
			SimpleDetailTravelDto simpleDetailTravelDto =
				mainTravelInfoDto.simpleDetailTravelListDto().detailTravelDtoList().get(i);

			SimpleDetailTravelResponse simpleDetailTravelResponse =
				mainTravelInfoResponse.simpleDetailTravelListResponse().detailTravelResponses().get(i);

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