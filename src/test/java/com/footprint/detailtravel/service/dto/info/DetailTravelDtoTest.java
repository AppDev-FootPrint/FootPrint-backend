package com.footprint.detailtravel.service.dto.info;

import static com.footprint.detailtravel.fixture.DetailTravelFixture.*;
import static org.assertj.core.api.Assertions.*;


import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.footprint.detailtravel.domain.DetailTravel;
import com.footprint.image.service.dto.ImageDto;
import com.footprint.price.service.dto.PriceDto;

/**
 * Created by ShinD on 2022/06/26.
 */
class DetailTravelDtoTest {
	@Test
	@DisplayName("DetailTravel, List<PriceDto>,  List<ImageDto> -> DetailTravelDto 변환 테스트")
	void fromTest() throws Exception {
		// given
		DetailTravel detailTravel = detailTravelHasId();
		List<PriceDto> priceDtos = detailTravel.getPrices().stream().map(PriceDto::from).toList();
		List<ImageDto> imageDtos = detailTravel.getImages().stream().map(ImageDto::from).toList();

		//when
		DetailTravelDto detailTravelDto = DetailTravelDto.of(detailTravel, priceDtos, imageDtos);

		// then
		assertThat(detailTravelDto.detailTravelId()).isEqualTo(detailTravel.getId());
		assertThat(detailTravelDto.mainTravelId()).isEqualTo(detailTravel.getMainTravel().getId()); //Main Travel 에서 수행해주므로 이곳에서는 NULL
		assertThat(detailTravelDto.title()).isEqualTo(detailTravel.getTitle());
		assertThat(detailTravelDto.review()).isEqualTo(detailTravel.getReview());
		assertThat(detailTravelDto.tip()).isEqualTo(detailTravel.getTip());
		assertThat(detailTravelDto.visitedDate()).isEqualTo(detailTravel.getVisitedDate());
		assertThat(detailTravelDto.address()).isEqualTo(detailTravel.getAddress());
		assertThat(detailTravelDto.createdAt()).isEqualTo(detailTravel.getCreatedAt());
		assertThat(detailTravelDto.priceDtoList()).containsExactlyElementsOf(priceDtos);
		assertThat(detailTravelDto.imageDtoList()).containsExactlyElementsOf(imageDtos);

	}
}