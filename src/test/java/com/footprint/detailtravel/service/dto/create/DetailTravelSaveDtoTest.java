package com.footprint.detailtravel.service.dto.create;

import static com.footprint.detailtravel.fixture.DetailTravelFixture.*;
import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.footprint.detailtravel.domain.DetailTravel;
import com.footprint.image.domain.Image;
import com.footprint.image.service.dto.ImageSaveDto;
import com.footprint.price.domain.Price;
import com.footprint.price.service.dto.PriceSaveDto;


/**
 * Created by ShinD on 2022/06/26.
 */
class DetailTravelSaveDtoTest {

	@Test
	@DisplayName("DetailTravelSaveDto -> DetailTravel 변환 테스트")
	void fromTest() throws Exception {
		// given
		DetailTravelSaveDto detailTravelSaveDto = detailTravelSaveDto(1);

		//when
		DetailTravel detailTravel = detailTravelSaveDto.toEntity();

		// then
		assertThat(detailTravel.getId()).isNull();
		assertThat(detailTravel.getMainTravel()).isNull(); //Main Travel 에서 수행해주므로 이곳에서는 NULL
		assertThat(detailTravel.getTitle()).isEqualTo(detailTravelSaveDto.title());
		assertThat(detailTravel.getReview()).isEqualTo(detailTravelSaveDto.review());
		assertThat(detailTravel.getTip()).isEqualTo(detailTravelSaveDto.tip());
		assertThat(detailTravel.getVisitedDate()).isEqualTo(detailTravelSaveDto.visitedDate());
		assertThat(detailTravel.getAddress()).isEqualTo(detailTravelSaveDto.address());

		List<Price> prices = detailTravel.getPrices();
		List<PriceSaveDto> priceSaveDtos = detailTravelSaveDto.priceSaveDtoList();
		for (int i = 0; i < prices.size(); i++) {
			Price price = prices.get(i);
			PriceSaveDto priceSaveDto = priceSaveDtos.get(i);
			assertThat(price.getItem()).isEqualTo(priceSaveDto.item());
			assertThat(price.getPriceInfo()).isEqualTo(priceSaveDto.priceInfo());
			assertThat(price.getId()).isNull();
			assertThat(price.getDetailTravel()).isEqualTo(detailTravel);
		}


		List<Image> images = detailTravel.getImages();
		List<ImageSaveDto> imageSaveDtos = detailTravelSaveDto.imageSaveDtoList();
		for (int i = 0; i < images.size(); i++) {
			Image image = images.get(i);
			ImageSaveDto imageSaveDto = imageSaveDtos.get(i);
			assertThat(image.getPath()).isEqualTo(imageSaveDto.path());
			assertThat(image.getId()).isNull();
			assertThat(image.getDetailTravel()).isEqualTo(detailTravel);
		}

	}
}