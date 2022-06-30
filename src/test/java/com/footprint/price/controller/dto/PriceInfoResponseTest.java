package com.footprint.price.controller.dto;

import static com.footprint.price.fixture.PriceFixture.*;
import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.footprint.price.service.dto.PriceDto;

/**
 * Created by ShinD on 2022/06/30.
 */
class PriceInfoResponseTest {

	private final ObjectMapper objectMapper = new ObjectMapper();

	private static final String EXPECT = """
  	{
  		"item":"%s",
  		"priceInfo":%d
  	}
		""".formatted(ITEM, PRICE);


	@Test
	@DisplayName("PriceInfoResponse -> json 변환 테스트")
	public void priceInfoResponseToJsonTest() throws Exception {
		//given
		PriceInfoResponse priceInfoResponse = priceInfoResponse();

		//when
		String content = objectMapper.writeValueAsString(priceInfoResponse);

		//then
		assertThat(content).isEqualTo(EXPECT.replaceAll(" ", "").replaceAll("\n","").replaceAll("\t",""));

	}



	@Test
	@DisplayName("PriceDto -> PriceInfoResponse 변환 테스트")
	public void priceDtoToPriceInfoResponseTest() throws Exception {
	    //given
		PriceDto priceDto = priceDto();

		//when
		PriceInfoResponse priceInfoResponse = PriceInfoResponse.from(priceDto);

		//then
		assertThat(priceInfoResponse.item()).isEqualTo(priceDto.item());
		assertThat(priceInfoResponse.priceInfo()).isEqualTo(priceDto.priceInfo());

	}

}