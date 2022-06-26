package com.footprint.maintravel.controller.dto.response;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.footprint.maintravel.fixture.MainTravelFixture;

/**
 * Created by ShinD on 2022/06/26.
 */
class MainTravelInfoResponseTest {

	//TODO Price 구현 후 추가하기
	//TODO 테스트 코드 작성할 때, 이렇게 하는게 맞나..? 그냥 둘다 ObjectMapper 쓰는거에 대해서는 어케 생각하는지
	private final ObjectMapper objectMapper = new ObjectMapper();
	private static final String EXPECT = """
	{ 
		"id" : 1 ,
		"writerInfo" : {"id":1,"username":"username","nickname":"nickname"},
		"title":"title",
		"startDate":"2022-06-15",
		"endDate":"2022-06-17",
		"createdAt":"2022-06-22T00:00:00",
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
					"title":"[title] : title (0 - 1)",
					"review":"[review] review (0 - 1)",
					"visitedDate":"2022-06-15",
					"address":{"address":"address","roadAddress":"roadAddress","mapX":1,"mapY":2},
					"createdAt":"2022-06-18T00:00:00"
				},
				{
					"detailTravelId":2,
					"title":"[title] : title (1 - 1)",
					"review":"[review] review (1 - 1)",
					"visitedDate":"2022-06-15",
					"address": { "address":"address","roadAddress":"roadAddress","mapX":1,"mapY":2},
					"createdAt":"2022-06-18T00:00:00"
				},
				{
					"detailTravelId":3,
					"title":"[title] : title (2 - 1)",
					"review":"[review] review (2 - 1)",
					"visitedDate":"2022-06-15",
					"address":{"address":"address","roadAddress":"roadAddress","mapX":1,"mapY":2},
					"createdAt":"2022-06-18T00:00:00"
				}
			]
		}
	}
		""".trim().replace("\n", "").replace(" ", "").replace("\t", "");


	@Test
	@DisplayName("MainTravelInfoResponse -> Json 변환 테스트")
	void jsonToCreateMainTravelRequestTest() throws Exception {
		//given, when
		MainTravelInfoResponse mainTravelInfoResponse = MainTravelFixture.mainTravelInfoResponse();
		assertThat(objectMapper.writeValueAsString(mainTravelInfoResponse).replace(" ", "")).isEqualTo(EXPECT);
	}



}