package com.footprint.maintravel.service.dto.save;

import static com.footprint.maintravel.fixture.MainTravelFixture.*;
import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.footprint.detailtravel.domain.DetailTravel;
import com.footprint.detailtravel.service.dto.create.DetailTravelSaveDto;
import com.footprint.image.domain.Image;
import com.footprint.image.service.dto.ImageSaveDto;
import com.footprint.maintravel.domain.MainTravel;
import com.footprint.price.domain.Price;
import com.footprint.price.service.dto.PriceSaveDto;

/**
 * Created by ShinD on 2022/06/26.
 */
class MainTravelSaveDtoTest {

	@Test
	@DisplayName("MainTravelSaveDto -> MainTravel 변환 테스트")
	void fromTest() throws Exception {
		// given
		MainTravelSaveDto mainTravelSaveDto = mainTravelSaveDto();

		//when
		MainTravel mainTravel = mainTravelSaveDto.toEntity();

		// then
		assertThat(mainTravel.getId()).isNull();
		assertThat(mainTravel.getTitle()).isEqualTo(mainTravelSaveDto.title());
		assertThat(mainTravel.getStartDate()).isEqualTo(mainTravelSaveDto.startDate());
		assertThat(mainTravel.getEndDate()).isEqualTo(mainTravelSaveDto.endDate());
		assertThat(mainTravel.isVisible()).isEqualTo(mainTravelSaveDto.isVisible());
		assertThat(mainTravel.isCompleted()).isEqualTo(mainTravelSaveDto.isCompleted());
		assertThat(mainTravel.getLikeNum()).isEqualTo(0);
		assertThat(mainTravel.getImagePath()).isEqualTo(mainTravelSaveDto.mainImagePath());
		assertThat(mainTravel.getCreatedAt()).isNull();
		assertThat(mainTravel.getDetailTravels().size()).isEqualTo(mainTravelSaveDto.detailTravelSaveDtoList().size());

		for (int i = 0; i < mainTravelSaveDto.detailTravelSaveDtoList().size(); i++) {
			DetailTravel detailTravel = mainTravel.getDetailTravels().get(i);
			DetailTravelSaveDto detailTravelSaveDto = mainTravelSaveDto.detailTravelSaveDtoList().get(i);
			assertThat(detailTravel.getId()).isNull();
			assertThat(detailTravel.getMainTravel()).isEqualTo(mainTravel);
			assertThat(detailTravel.getTitle()).isEqualTo(detailTravelSaveDto.title());
			assertThat(detailTravel.getReview()).isEqualTo(detailTravelSaveDto.review());
			assertThat(detailTravel.getTip()).isEqualTo(detailTravelSaveDto.tip());
			assertThat(detailTravel.getVisitedDate()).isEqualTo(detailTravelSaveDto.visitedDate());
			assertThat(detailTravel.getAddress()).isEqualTo(detailTravelSaveDto.address());

			int priceSize = detailTravel.getPrices().size();
			for (int j = 0; j < priceSize; j++) {
				Price price = detailTravel.getPrices().get(j);
				PriceSaveDto priceSaveDto = detailTravelSaveDto.priceSaveDtoList().get(j);
				assertThat(price.getPriceInfo()).isEqualTo(priceSaveDto.priceInfo());
				assertThat(price.getItem()).isEqualTo(priceSaveDto.item());
				assertThat(price.getDetailTravel()).isEqualTo(detailTravel);
				assertThat(price.getId()).isNull();
			}


			int imageSize = detailTravel.getImages().size();
			for (int j = 0; j < imageSize; j++) {
				Image image = detailTravel.getImages().get(j);
				ImageSaveDto imageSaveDto = detailTravelSaveDto.imageSaveDtoList().get(j);
				assertThat(image.getPath()).isEqualTo(imageSaveDto.path());

			}


		}

	}
}