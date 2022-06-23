/*
package com.footprint.detailtravel.fixture;

import static com.footprint.maintravel.fixture.MainTravelFixture.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import org.springframework.test.util.ReflectionTestUtils;

import com.footprint.detailtravel.domain.Address;
import com.footprint.detailtravel.domain.DetailTravel;
import com.footprint.detailtravel.service.dto.UpdateDetailTravelDto;
import com.footprint.maintravel.domain.MainTravel;
import com.footprint.member.domain.Member;

*/
/**
 * Created by ShinD on 2022/05/25.
 *//*

public class DetailTravelFixture {

	public static final Long DETAIL_TRAVEL_ID = 1L;
	public static final String TITLE = "title";
	public static final String UPDATE_TITLE = "update_title";
	public static final String REVIEW = "review";
	public static final String UPDATE_REVIEW = "update_review";
	public static final String TIP = "tip";
	public static final String UPDATE_TIP = "update_tip";
	public static final LocalDate VISITED_DATE = LocalDate.of(2022, 10, 4);
	public static final LocalDate UPDATE_VISITED_DATE = LocalDate.of(2021, 8, 3);


	public static final String ADDRESS = "address";
	public static final String UPDATE_ADDRESS = "update_address";
	public static final String ROAD_ADDRESS = "address";
	public static final String UPDATE_ROAD_ADDRESS = "update_address";
	public static final int MAP_X = 120;
	public static final int UPDATE_MAP_X = 2120;
	public static final int MAP_Y = 122;
	public static final int UPDATE_MAP_Y = 2122;

	public static final Address ADDRESS_OBJ = Address.builder().address(ADDRESS).roadAddress(ROAD_ADDRESS).mapX(MAP_X).mapY(MAP_Y).build();
	public static final Address UPDATE_ADDRESS_OBJ = Address.builder().address(UPDATE_ADDRESS).roadAddress(UPDATE_ROAD_ADDRESS).mapX(UPDATE_MAP_X).mapY(UPDATE_MAP_Y).build();
	public static final LocalDateTime CREATED_AT = LocalDateTime.of(2022, 3, 18, 10, 10);

	public static final Long MAIN_TRAVEL_WRITER_ID = 1L;
	public static final Long NO_AUTHORITY_MEMBER_ID = MAIN_TRAVEL_WRITER_ID + 1;


	public static DetailTravel getDetailTravelFixture() {
		DetailTravel detailTravel = DetailTravel.builder()
			.title(TITLE)
			.review(REVIEW)
			.tip(TIP)
			.visitedDate(VISITED_DATE)
			.address(ADDRESS_OBJ)
			.build();
		ReflectionTestUtils.setField(detailTravel, "createdAt", CREATED_AT);


		Member member = new Member("username", "password", "nickname");
		ReflectionTestUtils.setField(member, "id", MAIN_TRAVEL_WRITER_ID);

		MainTravel mainTravel = getMainTravel();
		ReflectionTestUtils.setField(mainTravel, "id", MAIN_TRAVEL_ID);

		ReflectionTestUtils.setField(mainTravel, "writer", member);


		detailTravel.setMainTravel(mainTravel);

		return detailTravel;
	}
	public static List<DetailTravel> getDetailTravelFixtureList(int size) {
		List<DetailTravel> detailTravelList = new ArrayList<>();
		for (int i = 0; i < size; i++) {
			DetailTravel detailTravelFixture = getDetailTravelFixture();
			ReflectionTestUtils.setField(detailTravelFixture, "id", DETAIL_TRAVEL_ID+i);
			detailTravelList.add(detailTravelFixture);
		}
		return detailTravelList;
	}



	public static UpdateDetailTravelDto getUpdateDetailTravelDto() {
		return new UpdateDetailTravelDto(DETAIL_TRAVEL_ID, MAIN_TRAVEL_ID, UPDATE_TITLE, UPDATE_REVIEW, UPDATE_TIP, UPDATE_VISITED_DATE, UPDATE_ADDRESS_OBJ);
	}


}
*/
