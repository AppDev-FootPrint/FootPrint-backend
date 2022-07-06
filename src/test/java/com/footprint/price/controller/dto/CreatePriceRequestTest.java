package com.footprint.price.controller.dto;

import static com.footprint.price.fixture.PriceFixture.*;
import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.footprint.price.service.dto.PriceSaveDto;

/**
 * Created by ShinD on 2022/06/30.
 */
class CreatePriceRequestTest {

	private final ObjectMapper objectMapper = new ObjectMapper();
	private static final String REQUEST_BODY = """
		{
			"item" : "%s",
			"priceInfo" : "%d"
		}
		""".formatted(ITEM, PRICE);


	@Test
	@DisplayName("Json -> CreatePriceRequest 변환 테스트")
	void jsonToCreateMainTravelRequestTest() throws Exception {
		//given, when
		CreatePriceRequest createPriceRequest = objectMapper.readValue(REQUEST_BODY, CreatePriceRequest.class);

		//then
		assertThat(createPriceRequest.item()).isEqualTo(ITEM);
		assertThat(createPriceRequest.priceInfo()).isEqualTo(PRICE);


	}


	@Test
	@DisplayName("CreatePriceRequest -> PriceSaveDto 변환 테스트")
	void createMainTravelRequestToMainTravelSaveDtoTest() throws Exception {
		//given
		CreatePriceRequest createPriceRequest = createPriceRequest();

		//when
		PriceSaveDto priceSaveDto = createPriceRequest.toServiceDto();

		//then
		assertThat(priceSaveDto.item()).isEqualTo(createPriceRequest.item());
		assertThat(priceSaveDto.priceInfo()).isEqualTo(createPriceRequest.priceInfo());
	}
}