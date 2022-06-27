package com.footprint.maintravel.service.dto.save;

import static com.footprint.maintravel.fixture.MainTravelFixture.*;
import static com.footprint.util.MappingTestUtil.*;
import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.footprint.detailtravel.domain.DetailTravel;
import com.footprint.detailtravel.service.dto.create.DetailTravelSaveDto;
import com.footprint.detailtravel.service.dto.create.ImageSaveDto;
import com.footprint.detailtravel.service.dto.create.PriceSaveDto;
import com.footprint.maintravel.domain.MainTravel;

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
			assertThat(detailTravel.getPrices()).containsAll(detailTravelSaveDto.priceSaveDtoList().stream().map(PriceSaveDto::toEntity).toList());

			assertThat(detailTravel.getImages())
				.isEqualTo(mappingToList(detailTravelSaveDto.imageSaveDtoList(), ImageSaveDto::toEntity));
		}

	}
}