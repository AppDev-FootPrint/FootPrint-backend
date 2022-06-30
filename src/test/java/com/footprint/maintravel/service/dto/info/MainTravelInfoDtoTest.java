package com.footprint.maintravel.service.dto.info;

import static com.footprint.maintravel.fixture.MainTravelFixture.*;
import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.footprint.detailtravel.fixture.DetailTravelFixture;
import com.footprint.detailtravel.service.dto.info.SimpleDetailTravelListDto;
import com.footprint.maintravel.domain.MainTravel;
import com.footprint.member.service.dto.MemberInfoDto;

/**
 * Created by ShinD on 2022/06/26.
 */
class MainTravelInfoDtoTest {
	//TODO Comment, Heart, Scrap 구현 후 추가하기
	@Test
	@DisplayName("MainTravel -> MainTravelInfoDto 변환 테스트")
	void fromTest() throws Exception {
		// given, when
		MainTravel mainTravel = mainTravelHasId();
		SimpleDetailTravelListDto simpleDetailTravelListDto = DetailTravelFixture.simpleDetailTravelListDto(mainTravel.getId(), 3);
		MainTravelInfoDto mainTravelInfoDto = MainTravelInfoDto.from(mainTravel, simpleDetailTravelListDto);

		// then
		assertThat(mainTravelInfoDto.id()).isEqualTo(mainTravel.getId());
		assertThat(mainTravelInfoDto.writerInfo()).isEqualTo(MemberInfoDto.from(mainTravel.getWriter()));
		assertThat(mainTravelInfoDto.title()).isEqualTo(mainTravel.getTitle());
		assertThat(mainTravelInfoDto.startDate()).isEqualTo(mainTravel.getStartDate().toString());
		assertThat(mainTravelInfoDto.endDate()).isEqualTo(mainTravel.getEndDate().toString());
		assertThat(mainTravelInfoDto.createdAt()).isEqualTo(mainTravel.getCreatedAt().toString());
		assertThat(mainTravelInfoDto.isVisible()).isEqualTo(mainTravel.isVisible());
		assertThat(mainTravelInfoDto.isCompleted()).isEqualTo(mainTravel.isCompleted());
		assertThat(mainTravelInfoDto.mainImagePath()).isEqualTo(mainTravel.getImagePath());
		assertThat(mainTravelInfoDto.likeNum()).isEqualTo(mainTravel.getLikeNum());
		assertThat(mainTravelInfoDto.simpleDetailTravelListDto()).isEqualTo(simpleDetailTravelListDto);

	}
}