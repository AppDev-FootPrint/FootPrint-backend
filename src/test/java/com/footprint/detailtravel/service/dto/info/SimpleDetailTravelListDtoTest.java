package com.footprint.detailtravel.service.dto.info;

import static com.footprint.detailtravel.fixture.DetailTravelFixture.*;
import static java.util.stream.IntStream.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.stream.IntStream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.footprint.detailtravel.domain.DetailTravel;

/**
 * Created by ShinD on 2022/06/26.
 */
class SimpleDetailTravelListDtoTest {

	private static final int SIZE = 10;
	@Test
	@DisplayName("List<DetailTravel> -> SimpleDetailTravelListDto 변환 테스트")
	void fromTest() throws Exception {
		// given

		List<DetailTravel> detailTravelList = range(0, SIZE).mapToObj(i -> detailTravelHasId()).toList();

		//when
		SimpleDetailTravelListDto simpleDetailTravelListDto = SimpleDetailTravelListDto.from(detailTravelList);

		// then
		assertThat(simpleDetailTravelListDto.total()).isEqualTo(SIZE);

		for (int i = 0; i < SIZE; i++) {
			SimpleDetailTravelDto simpleDetailTravelDto = simpleDetailTravelListDto.detailTravelDtoList().get(i);
			DetailTravel detailTravel = detailTravelList.get(i);
			assertThat(simpleDetailTravelDto.detailTravelId()).isEqualTo(detailTravel.getId());
			assertThat(simpleDetailTravelDto.mainTravelId()).isEqualTo(detailTravel.getMainTravel().getId());
			assertThat(simpleDetailTravelDto.title()).isEqualTo(detailTravel.getTitle());
			assertThat(simpleDetailTravelDto.review()).isEqualTo(detailTravel.getReview());
			assertThat(simpleDetailTravelDto.visitedDate()).isEqualTo(detailTravel.getVisitedDate());
			assertThat(simpleDetailTravelDto.address()).isEqualTo(detailTravel.getAddress());
			assertThat(simpleDetailTravelDto.createdAt()).isEqualTo(detailTravel.getCreatedAt());

		}

	}
}