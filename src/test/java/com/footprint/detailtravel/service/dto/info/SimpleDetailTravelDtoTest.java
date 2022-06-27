package com.footprint.detailtravel.service.dto.info;

import static com.footprint.detailtravel.fixture.DetailTravelFixture.*;
import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.footprint.detailtravel.domain.DetailTravel;

/**
 * Created by ShinD on 2022/06/26.
 */
class SimpleDetailTravelDtoTest {

	@Test
	@DisplayName("DetailTravel -> SimpleDetailTravelDto 변환 테스트")
	void fromTest() throws Exception {
		// given
		DetailTravel detailTravel = detailTravelHasId();

		//when
		SimpleDetailTravelDto simpleDetailTravelDto = SimpleDetailTravelDto.from(detailTravel);

		// then
		assertThat(simpleDetailTravelDto.detailTravelId()).isEqualTo(detailTravel.getId());
		assertThat(simpleDetailTravelDto.mainTravelId()).isEqualTo(detailTravel.getMainTravel().getId()); //Main Travel 에서 수행해주므로 이곳에서는 NULL
		assertThat(simpleDetailTravelDto.title()).isEqualTo(detailTravel.getTitle());
		assertThat(simpleDetailTravelDto.review()).isEqualTo(detailTravel.getReview());
		assertThat(simpleDetailTravelDto.visitedDate()).isEqualTo(detailTravel.getVisitedDate());
		assertThat(simpleDetailTravelDto.address()).isEqualTo(detailTravel.getAddress());
		assertThat(simpleDetailTravelDto.createdAt()).isEqualTo(detailTravel.getCreatedAt());

	}
}