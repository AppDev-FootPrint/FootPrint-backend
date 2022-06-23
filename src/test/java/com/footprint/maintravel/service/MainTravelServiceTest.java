/*
package com.footprint.maintravel.service;

import static com.footprint.maintravel.fixture.MainTravelFixture.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.footprint.maintravel.domain.MainTravel;
import com.footprint.maintravel.exception.MainTravelException;
import com.footprint.maintravel.repository.MainTravelRepository;
import com.footprint.maintravel.service.dto.MainTravelDto;

@ExtendWith(MockitoExtension.class)
class MainTravelServiceTest {
	private final MainTravelRepository mainTravelRepository = mock(MainTravelRepository.class);

	@InjectMocks
	private MainTravelService mainTravelService = new MainTravelServiceImpl(mainTravelRepository);

	private final ObjectMapper objectMapper = new ObjectMapper();

	@Test
	@DisplayName("성공적으로 MainTravel을 불러오는지 테스트")
	void getMainTravelSuccessTest() throws Exception {
		//given
		given(mainTravelRepository.findById(MAIN_TRAVEL_ID)).willReturn(Optional.of(getMainTravel()));

		//when
		MainTravelDto mainTravelDto = mainTravelService.getMainTravel(MAIN_TRAVEL_ID);

		//then
		assertEquals(getMainTravelDto().title(), mainTravelDto.title());
		assertEquals(getMainTravelDto().startDate(), mainTravelDto.startDate());
		assertEquals(getMainTravelDto().endDate(), mainTravelDto.endDate());
		assertEquals(getMainTravelDto().isVisible(), mainTravelDto.isVisible());
		assertEquals(getMainTravelDto().isCompleted(), mainTravelDto.isCompleted());
	}

	@Test
	@DisplayName("찾으려는 MainTravel이 존재하지 않을 때 테스트")
	void getMainTravelFailTest() throws Exception {
		//given
		given(mainTravelRepository.findById(MAIN_TRAVEL_ID)).willReturn(Optional.empty());

		//when, then
		assertThrows(MainTravelException.class, () -> mainTravelService.getMainTravel(MAIN_TRAVEL_ID));
	}

	@Test
	@DisplayName("MainTravel 저장 성공 테스트")
	void saveMainTravelSuccessTest() throws Exception {
		//given
		MainTravel mainTravel = getMainTravel();
		ReflectionTestUtils.setField(mainTravel, "id", MAIN_TRAVEL_ID);
		given(mainTravelRepository.save(any())).willReturn(mainTravel);

		//when
		Long savedMainTravelId = mainTravelService.saveMainTravel(getMainTravelSaveDto());

		//then
		assertEquals(MAIN_TRAVEL_ID, savedMainTravelId);
	}

	@Test
	@DisplayName("MainTravel 수정 성공 테스트")
	void updateMainTravelSuccessTest() throws Exception {
		//given
		MainTravel mainTravel = getMainTravel();
		ReflectionTestUtils.setField(mainTravel, "id", MAIN_TRAVEL_ID);
		given(mainTravelRepository.findById(MAIN_TRAVEL_ID)).willReturn(Optional.of(mainTravel));

		//when
		Long savedMainTravelId = mainTravelService.updateMainTravel(getMainTravelUpdateDto());

		//then
		assertEquals(MAIN_TRAVEL_ID, savedMainTravelId);
		assertEquals(UPDATE_TITLE, mainTravel.getTitle());
	}

	@Test
	@DisplayName("MainTravel 삭제 테스트")
	void deleteMainTravelTest() throws Exception {
		//given
		MainTravel mainTravel = getMainTravel();
		given(mainTravelRepository.findById(MAIN_TRAVEL_ID)).willReturn(Optional.of(mainTravel));

		//when
		mainTravelService.deleteMainTravel(MAIN_TRAVEL_ID);

		//then
		verify(mainTravelRepository, times(1)).delete(mainTravel);
	}
}*/
