package com.footprint.price.service.dto;

import static com.footprint.price.fixture.PriceFixture.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.footprint.price.domain.Price;

/**
 * Created by ShinD on 2022/06/30.
 */
class PriceDtoTest {

	@Test
	@DisplayName("Price -> PriceSaveDto 변환 테스트")
	public void priceToPriceSaveDtoTest() throws Exception {
		//given
		Price price = priceHasId();

		//when
		PriceDto priceDto = PriceDto.from(price);

		//then
		Assertions.assertThat(priceDto.item()).isEqualTo(price.getItem());
		Assertions.assertThat(priceDto.priceInfo()).isEqualTo(price.getPriceInfo());

	}

}