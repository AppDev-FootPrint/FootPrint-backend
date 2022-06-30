package com.footprint.price.service.dto;

import static com.footprint.price.fixture.PriceFixture.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.footprint.price.domain.Price;

/**
 * Created by ShinD on 2022/06/30.
 */
class PriceSaveDtoTest {

	@Test
	@DisplayName("PriceSaveDto -> Price 변환 테스트")
	public void priceSaveDtoToPriceTest() throws Exception {
		//given
		PriceSaveDto priceSaveDto = priceSaveDto();

		//when
		Price price = priceSaveDto.toEntity();

		//then
		Assertions.assertThat(price.getId()).isNull();
		Assertions.assertThat(price.getDetailTravel()).isNull();
		Assertions.assertThat(price.getItem()).isEqualTo(price.getItem());
		Assertions.assertThat(price.getPriceInfo()).isEqualTo(price.getPriceInfo());
	}

}