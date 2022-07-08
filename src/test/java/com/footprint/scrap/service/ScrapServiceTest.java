package com.footprint.scrap.service;

import static com.footprint.maintravel.fixture.MainTravelFixture.*;
import static com.footprint.member.fixture.MemberFixture.*;
import static com.footprint.member.fixture.MemberFixture.MEMBER_ID;
import static com.footprint.scrap.fixture.ScrapFixture.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.BDDMockito.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import com.footprint.maintravel.exception.MainTravelException;
import com.footprint.maintravel.repository.MainTravelRepository;
import com.footprint.member.repository.MemberRepository;
import com.footprint.scrap.domain.Scrap;
import com.footprint.scrap.exception.ScrapException;
import com.footprint.scrap.repository.ScrapRepository;
import com.footprint.scrap.service.dto.ScrapDto;

@ExtendWith(MockitoExtension.class)
class ScrapServiceTest {
	private final ScrapRepository scrapRepository = mock(ScrapRepository.class);
	private final MemberRepository memberRepository = mock(MemberRepository.class);
	private final MainTravelRepository mainTravelRepository = mock(MainTravelRepository.class);

	@InjectMocks
	private ScrapService scrapService = new ScrapServiceImpl(scrapRepository, memberRepository, mainTravelRepository);

	@Test
	@DisplayName("Scrap에 성공한 경우 테스트")
	void saveScrapTest() {
		//given
		given(scrapRepository.existsScrapByMemberIdAndMainTravelId(MEMBER_ID, MAIN_TRAVEL_ID)).willReturn(false);
		given(memberRepository.findById(MEMBER_ID)).willReturn(Optional.of(defaultMember()));
		given(mainTravelRepository.findById(MAIN_TRAVEL_ID)).willReturn(Optional.of(mainTravelHasId()));
		given(scrapRepository.save(any())).willReturn(getScrap());

		//when
		Long scrapId = scrapService.saveScrap(MEMBER_ID, MAIN_TRAVEL_ID);

		//then
		assertEquals(getScrap().getId(), scrapId);
	}

	@Test
	@DisplayName("Scrap에 실패한 경우 테스트 - 해당 글의 스크랩이 존재하는 경우")
	void saveScrapFailTestConflictScrap() {
		//given, when
		given(scrapRepository.existsScrapByMemberIdAndMainTravelId(MEMBER_ID, MAIN_TRAVEL_ID)).willReturn(true);

		//then
		assertThrows(ScrapException.class, () -> scrapService.saveScrap(MEMBER_ID, MAIN_TRAVEL_ID));
	}

	@Test
	@DisplayName("Scrap에 실패한 경우 테스트 - 해당 글이 존재하지 않는 경우")
	void saveScrapFailTestNotFoundMainTravel() {
		//given, when
		given(scrapRepository.existsScrapByMemberIdAndMainTravelId(MEMBER_ID, MAIN_TRAVEL_ID)).willReturn(false);
		given(memberRepository.findById(MEMBER_ID)).willReturn(Optional.of(defaultMember()));
		given(mainTravelRepository.findById(MAIN_TRAVEL_ID)).willThrow(MainTravelException.class);

		//then
		assertThrows(MainTravelException.class, () -> scrapService.saveScrap(MEMBER_ID, MAIN_TRAVEL_ID));
	}

	@Test
	@DisplayName("Scrap 삭제 테스트")
	void deleteScrapTest() {
		//given
		Scrap scrap = getScrap();
		given(scrapRepository.findById(SCRAP_ID)).willReturn(Optional.of(scrap));

		//when
		scrapService.deleteScrap(SCRAP_ID);

		//then
		verify(scrapRepository, times(1)).delete(scrap);
	}

	@Test
	@DisplayName("해당 유저의 Scrap 목록 가져오기 테스트")
	void getScrapsTest() {
		//given
		given(scrapRepository.findAllWithMainTravelByMemberId(MEMBER_ID)).willReturn(List.of(getScrap()));

		//when
		List<ScrapDto> scraps = scrapService.getScraps(MEMBER_ID);

		//then
		assertEquals(1, scraps.size());
	}
}