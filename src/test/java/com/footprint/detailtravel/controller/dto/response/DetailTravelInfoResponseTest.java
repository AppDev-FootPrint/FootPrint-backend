package com.footprint.detailtravel.controller.dto.response;

import static com.footprint.detailtravel.fixture.DetailTravelFixture.*;
import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.footprint.detailtravel.service.dto.info.DetailTravelDto;
import com.footprint.detailtravel.service.dto.info.ImageDto;
import com.footprint.detailtravel.service.dto.info.PriceDto;

/**
 * Created by ShinD on 2022/06/26.
 */
class DetailTravelInfoResponseTest {
	//TODO Price, Image 구현 후 추가하기
	private final ObjectMapper objectMapper = new ObjectMapper();
	private static final String EXPECT = """
	{
		"id":1,
		"mainTravelId":1,
		"title":"title",
		"review":"review",
		"tip":"tip",
		"visitedDate":"2022-06-15",
		"address":{"address":"address","roadAddress":"roadAddress","mapX":1,"mapY":2},
		"createdAt":"2022-06-18T00:00:00",
		"priceInfoResponseList":[],
		"imageInfoResponseList":[]
		}
		""";


	@Test
	@DisplayName("DetailTravelInfoResponse -> Json 변환 테스트")
	void detailTravelInfoResponseToJsonTest() throws Exception {
		//given
		DetailTravelInfoResponse detailTravelInfoResponse = detailTravelInfoResponse();

		//when, then
		assertThat(objectMapper.writeValueAsString(detailTravelInfoResponse).replace(" ", ""))
			.isEqualTo(EXPECT.trim().replace("\n", "").replace(" ", "").replace("\t", ""));
	}


	@Test
	@DisplayName("DetailTravelDto -> DetailTravelInfoResponse 변환 테스트")
	void detailTravelDtoToDetailTravelInfoResponseTest() throws Exception {
		//given
		DetailTravelDto detailTravelDto = detailTravelDto();

		//when
		DetailTravelInfoResponse detailTravelInfoResponse = DetailTravelInfoResponse.from(detailTravelDto);


		//then
		assertThat(detailTravelInfoResponse.id()).isEqualTo(detailTravelDto.detailTravelId());
		assertThat(detailTravelInfoResponse.mainTravelId()).isEqualTo(detailTravelDto.mainTravelId());
		assertThat(detailTravelInfoResponse.title()).isEqualTo(detailTravelDto.title());
		assertThat(detailTravelInfoResponse.review()).isEqualTo(detailTravelDto.review());
		assertThat(detailTravelInfoResponse.tip()).isEqualTo(detailTravelDto.tip());
		assertThat(detailTravelInfoResponse.visitedDate()).isEqualTo(detailTravelDto.visitedDate().toString());
		assertThat(detailTravelInfoResponse.address()).isEqualTo(detailTravelDto.address());
		assertThat(detailTravelInfoResponse.createdAt()).isEqualTo(detailTravelDto.createdAt().toString());



		List<PriceInfoResponse> priceInfoResponses = detailTravelInfoResponse.priceInfoResponseList();
		List<PriceDto> priceDtos = detailTravelDto.priceDtoList();
		for (int i = 0; i < priceInfoResponses.size(); i++) {
			PriceInfoResponse priceInfoResponse = priceInfoResponses.get(i);
			PriceDto priceDto = priceDtos.get(i);
			assertThat(priceInfoResponse.item()).isEqualTo(priceDto.item());
			assertThat(priceInfoResponse.priceInfo()).isEqualTo(priceDto.priceInfo());
		}


		List<ImageInfoResponse> imageInfoResponses = detailTravelInfoResponse.imageInfoResponseList();
		List<ImageDto> imageDtos = detailTravelDto.imageDtoList();
		for (int i = 0; i < imageInfoResponses.size(); i++) {
			ImageInfoResponse imageInfoResponse = imageInfoResponses.get(i);
			ImageDto imageDto = imageDtos.get(i);
			assertThat(imageInfoResponse.path()).isEqualTo(imageDto.path());
		}
	}
}