/*
package com.footprint.detailtravel.service;

import static com.footprint.detailtravel.fixture.DetailTravelFixture.TITLE;
import static com.footprint.detailtravel.fixture.DetailTravelFixture.UPDATE_TITLE;
import static com.footprint.detailtravel.fixture.DetailTravelFixture.*;
import static com.footprint.maintravel.fixture.MainTravelFixture.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.IntStream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import com.footprint.detailtravel.domain.DetailTravel;
import com.footprint.detailtravel.exception.DetailTravelException;
import com.footprint.detailtravel.exception.DetailTravelExceptionType;
import com.footprint.detailtravel.repository.DetailTravelRepository;
import com.footprint.detailtravel.service.dto.DetailTravelDto;
import com.footprint.detailtravel.service.dto.DetailTravelListDto;
import com.footprint.maintravel.repository.MainTravelRepository;
import com.footprint.member.repository.MemberRepository;

*/
/**
 * Created by ShinD on 2022/05/25.
 *//*

@ExtendWith(MockitoExtension.class)
class DetailTravelServiceImplTest {


	private final DetailTravelRepository detailTravelRepository = mock(DetailTravelRepository.class);
	private final MainTravelRepository mainTravelRepository = mock(MainTravelRepository.class);
	private final MemberRepository memberRepository = mock(MemberRepository.class);

	@InjectMocks
	private DetailTravelService detailTravelService = new DetailTravelServiceImpl(detailTravelRepository, mainTravelRepository, memberRepository);


	@Test
	@DisplayName("Detail Travel 수정 성공")
	void successUpdateDetailTravel() throws Exception {
		//given
		DetailTravel detailTravel = getDetailTravelFixture();
		ReflectionTestUtils.setField(detailTravel, "id", DETAIL_TRAVEL_ID);
		given(detailTravelRepository.findById(DETAIL_TRAVEL_ID)).willReturn(Optional.ofNullable(detailTravel));


		//when
		detailTravelService.update(MAIN_TRAVEL_WRITER_ID, getUpdateDetailTravelDto());


		//then
		assertEquals(DETAIL_TRAVEL_ID, detailTravel.getId());
		assertEquals(MAIN_TRAVEL_ID, detailTravel.getMainTravel().getId());
		assertEquals(MAIN_TRAVEL_WRITER_ID, detailTravel.getMainTravel().getWriter().getId());

		assertEquals(UPDATE_TITLE, detailTravel.getTitle());
		assertEquals(UPDATE_REVIEW, detailTravel.getReview());
		assertEquals(UPDATE_TIP, detailTravel.getTip());
		assertEquals(UPDATE_VISITED_DATE, detailTravel.getVisitedDate());

		assertEquals(UPDATE_ADDRESS, detailTravel.getAddress().getAddress());
		assertEquals(UPDATE_ROAD_ADDRESS, detailTravel.getAddress().getRoadAddress());
		assertEquals(UPDATE_MAP_X, detailTravel.getAddress().getMapX());
		assertEquals(UPDATE_MAP_Y, detailTravel.getAddress().getMapY());
	}

	@Test
	@DisplayName("Detail Travel 수정 실패 - (원인 : 없는 Detail Travel)")
	void failUpdateDetailTravelCauseNoDetailTravel() throws Exception {
		//given
		DetailTravel detailTravel = getDetailTravelFixture();
		ReflectionTestUtils.setField(detailTravel, "id", DETAIL_TRAVEL_ID);
		given(detailTravelRepository.findById(DETAIL_TRAVEL_ID)).willReturn(Optional.ofNullable(null));


		//when
		DetailTravelExceptionType exceptionType = (DetailTravelExceptionType) assertThrows(DetailTravelException.class,
			() -> detailTravelService.update(MAIN_TRAVEL_WRITER_ID, getUpdateDetailTravelDto())).getExceptionType();


		//then
		assertEquals(DetailTravelExceptionType.NOT_FOUND, exceptionType);
		assertEquals(DETAIL_TRAVEL_ID, detailTravel.getId());
		assertEquals(MAIN_TRAVEL_ID, detailTravel.getMainTravel().getId());
		assertEquals(MAIN_TRAVEL_WRITER_ID, detailTravel.getMainTravel().getWriter().getId());

		assertEquals(TITLE, detailTravel.getTitle());
		assertNotEquals(UPDATE_TITLE, detailTravel.getTitle());
		assertEquals(REVIEW, detailTravel.getReview());
		assertEquals(TIP, detailTravel.getTip());
		assertEquals(VISITED_DATE, detailTravel.getVisitedDate());

		assertEquals(ADDRESS, detailTravel.getAddress().getAddress());
		assertEquals(ROAD_ADDRESS, detailTravel.getAddress().getRoadAddress());
		assertEquals(MAP_X, detailTravel.getAddress().getMapX());
		assertEquals(MAP_Y, detailTravel.getAddress().getMapY());
	}

	@Test
	@DisplayName("Detail Travel 수정 실패 - (원인 : 권한 없는 유저)")
	void failUpdateDetailTravelCauseMemberNoAuthority() throws Exception {
		//given
		DetailTravel detailTravel = getDetailTravelFixture();
		ReflectionTestUtils.setField(detailTravel, "id", DETAIL_TRAVEL_ID);
		given(detailTravelRepository.findById(DETAIL_TRAVEL_ID)).willReturn(Optional.ofNullable(detailTravel));


		//when
		DetailTravelExceptionType exceptionType = (DetailTravelExceptionType) assertThrows(DetailTravelException.class,
			() -> detailTravelService.update(NO_AUTHORITY_MEMBER_ID, getUpdateDetailTravelDto())).getExceptionType();


		//then
		assertEquals(DetailTravelExceptionType.NO_AUTHORITY, exceptionType);
		assertEquals(DETAIL_TRAVEL_ID, detailTravel.getId());
		assertEquals(MAIN_TRAVEL_ID, detailTravel.getMainTravel().getId());
		assertEquals(MAIN_TRAVEL_WRITER_ID, detailTravel.getMainTravel().getWriter().getId());

		assertEquals(TITLE, detailTravel.getTitle());
		assertNotEquals(UPDATE_TITLE, detailTravel.getTitle());
		assertEquals(REVIEW, detailTravel.getReview());
		assertEquals(TIP, detailTravel.getTip());
		assertEquals(VISITED_DATE, detailTravel.getVisitedDate());

		assertEquals(ADDRESS, detailTravel.getAddress().getAddress());
		assertEquals(ROAD_ADDRESS, detailTravel.getAddress().getRoadAddress());
		assertEquals(MAP_X, detailTravel.getAddress().getMapX());
		assertEquals(MAP_Y, detailTravel.getAddress().getMapY());
	}


	@Test
	@DisplayName("Detail Travel 삭제 성공")
	void successDeleteDetailTravel() throws Exception {
		//given
		DetailTravel detailTravel = getDetailTravelFixture();
		given(detailTravelRepository.findById(DETAIL_TRAVEL_ID)).willReturn(Optional.ofNullable(detailTravel));


		//when
		detailTravelService.delete(MAIN_TRAVEL_WRITER_ID, DETAIL_TRAVEL_ID);

		//then
		verify(detailTravelRepository, times(1)).delete(detailTravel);
	}


	@Test
	@DisplayName("Detail Travel 삭제 실패 - (원인 : 없는 Detail Travel)")
	void failDeleteDetailTravelCauseNoDetailTravel() throws Exception {
		//given
		DetailTravel detailTravel = getDetailTravelFixture();
		ReflectionTestUtils.setField(detailTravel, "id", DETAIL_TRAVEL_ID);
		given(detailTravelRepository.findById(DETAIL_TRAVEL_ID)).willReturn(Optional.ofNullable(null));


		//when
		DetailTravelExceptionType exceptionType = (DetailTravelExceptionType) assertThrows(DetailTravelException.class,
			() -> detailTravelService.delete(MAIN_TRAVEL_WRITER_ID, DETAIL_TRAVEL_ID)).getExceptionType();


		//then
		verify(detailTravelRepository, times(0)).delete(detailTravel);

	}

	@Test
	@DisplayName("Detail Travel 수정 실패 - (원인 : 권한 없는 유저)")
	void failDeleteDetailTravelCauseMemberNoAuthority() throws Exception {
		//given
		DetailTravel detailTravel = getDetailTravelFixture();
		ReflectionTestUtils.setField(detailTravel, "id", DETAIL_TRAVEL_ID);
		given(detailTravelRepository.findById(DETAIL_TRAVEL_ID)).willReturn(Optional.ofNullable(detailTravel));


		//when
		DetailTravelExceptionType exceptionType = (DetailTravelExceptionType) assertThrows(DetailTravelException.class,
			() -> detailTravelService.delete(NO_AUTHORITY_MEMBER_ID, DETAIL_TRAVEL_ID)).getExceptionType();


		//then
		verify(detailTravelRepository, times(0)).delete(detailTravel);
		assertEquals(DetailTravelExceptionType.NO_AUTHORITY, exceptionType);
		assertEquals(DETAIL_TRAVEL_ID, detailTravel.getId());
		assertEquals(MAIN_TRAVEL_ID, detailTravel.getMainTravel().getId());
		assertEquals(MAIN_TRAVEL_WRITER_ID, detailTravel.getMainTravel().getWriter().getId());

		assertEquals(TITLE, detailTravel.getTitle());
		assertNotEquals(UPDATE_TITLE, detailTravel.getTitle());
		assertEquals(REVIEW, detailTravel.getReview());
		assertEquals(TIP, detailTravel.getTip());
		assertEquals(VISITED_DATE, detailTravel.getVisitedDate());

		assertEquals(ADDRESS, detailTravel.getAddress().getAddress());
		assertEquals(ROAD_ADDRESS, detailTravel.getAddress().getRoadAddress());
		assertEquals(MAP_X, detailTravel.getAddress().getMapX());
		assertEquals(MAP_Y, detailTravel.getAddress().getMapY());
	}



	@Test
	@DisplayName("Detail Travel 조회 성공")
	void successGetDetailTravel() throws Exception {
		//given
		DetailTravel detailTravel = getDetailTravelFixture();
		ReflectionTestUtils.setField(detailTravel, "id", DETAIL_TRAVEL_ID);
		given(detailTravelRepository.findById(DETAIL_TRAVEL_ID)).willReturn(Optional.ofNullable(detailTravel));


		//when
		DetailTravelDto detailTravelDto = detailTravelService.getById(DETAIL_TRAVEL_ID);

		//then
		assertEquals(DETAIL_TRAVEL_ID, detailTravelDto.getDetailTravelId());
		assertEquals(TITLE, detailTravelDto.getTitle());
		assertEquals(REVIEW, detailTravelDto.getReview());
		assertEquals(TIP, detailTravelDto.getTip());
		assertEquals(VISITED_DATE, detailTravelDto.getVisitedDate());
		assertEquals(ADDRESS, detailTravelDto.getAddress().getAddress());
		assertEquals(ROAD_ADDRESS, detailTravelDto.getAddress().getRoadAddress());
		assertEquals(MAP_X, detailTravelDto.getAddress().getMapX());
		assertEquals(MAP_Y, detailTravelDto.getAddress().getMapY());
	}

	@Test
	@DisplayName("Detail Travel 조회 실패 - (원인 : 없는 Detail Travel)")
	void failGetDetailTravelCauseNoDetailTravel() throws Exception {
		//given
		DetailTravel detailTravel = getDetailTravelFixture();
		given(detailTravelRepository.findById(DETAIL_TRAVEL_ID)).willReturn(Optional.ofNullable(null));


		//when, then
		DetailTravelExceptionType exceptionType = (DetailTravelExceptionType)assertThrows(DetailTravelException.class,
			() -> detailTravelService.getById(DETAIL_TRAVEL_ID)).getExceptionType();

		assertEquals(DetailTravelExceptionType.NOT_FOUND ,exceptionType);

	}

	@Test
	@DisplayName("Main Travel에 속한 Detail Travel 조회 (여러개 있는 케이스)")
	void successGetAllDetailTravelInMainTravel() throws Exception {
		//given
		final int SIZE = 5;
		given(detailTravelRepository.findAllByMainTravelId(MAIN_TRAVEL_ID)).willReturn(getDetailTravelFixtureList(SIZE));


		//when
		DetailTravelListDto detailTravelListDto = detailTravelService.getAllByMainTravelId(MAIN_TRAVEL_ID);

		//then
		assertEquals(SIZE, detailTravelListDto.total());
		IntStream.range(0, SIZE).forEach(i ->{
			DetailTravelDto detailTravelDto = detailTravelListDto.detailTravelDtoList().get(i);
			assertEquals(TITLE, detailTravelDto.getTitle());
			assertEquals(REVIEW, detailTravelDto.getReview());
			assertEquals(TIP, detailTravelDto.getTip());
			assertEquals(VISITED_DATE, detailTravelDto.getVisitedDate());
			assertEquals(ADDRESS, detailTravelDto.getAddress().getAddress());
			assertEquals(ROAD_ADDRESS, detailTravelDto.getAddress().getRoadAddress());
			assertEquals(MAP_X, detailTravelDto.getAddress().getMapX());
			assertEquals(MAP_Y, detailTravelDto.getAddress().getMapY());
		});
	}


	@Test
	@DisplayName("Main Travel에 속한 Detail Travel 조회 (0 개인 케이스)")
	void successGetAllDetailTravelInMainTravel_Case_Zero() throws Exception {
		//given
		final int SIZE = 0;
		given(detailTravelRepository.findAllByMainTravelId(MAIN_TRAVEL_ID)).willReturn(getDetailTravelFixtureList(SIZE));


		//when
		DetailTravelListDto detailTravelListDto = detailTravelService.getAllByMainTravelId(MAIN_TRAVEL_ID);

		//then
		assertEquals(SIZE, detailTravelListDto.total());
		assertTrue(detailTravelListDto.detailTravelDtoList().isEmpty());
	}

	@Test
	@DisplayName("Main Travel에 속한 Detail Travel 조회시 결과가 0 (원인 : Main Travel이 존재하지 않음)")
	void failGetAllDetailTravelInMainTravelCauseNoMainTravel() throws Exception {
		//given
		given(detailTravelRepository.findAllByMainTravelId(MAIN_TRAVEL_ID)).willReturn(new ArrayList<>());


		//when
		DetailTravelListDto detailTravelListDto = detailTravelService.getAllByMainTravelId(MAIN_TRAVEL_ID);

		//then
		assertEquals(0, detailTravelListDto.total());
		assertTrue(detailTravelListDto.detailTravelDtoList().isEmpty());

	}

}*/
