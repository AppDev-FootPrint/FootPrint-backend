package com.footprint.maintravel.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.footprint.maintravel.controller.dto.MainTravelResponse;
import com.footprint.maintravel.domain.MainTravel;
import com.footprint.maintravel.exception.MainTravelException;
import com.footprint.maintravel.exception.MainTravelExceptionType;
import com.footprint.maintravel.repository.MainTravelRepository;
import com.footprint.maintravel.service.dto.MainTravelDto;
import com.footprint.maintravel.service.dto.MainTravelSaveDto;
import com.footprint.maintravel.service.dto.MainTravelUpdateDto;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class MainTravelServiceImpl implements MainTravelService {
	private final MainTravelRepository mainTravelRepository;

	@Override
	@Transactional(readOnly = true)
	public MainTravelDto getMainTravel(Long travelId) {
		MainTravel mainTravel = mainTravelRepository.findById(travelId)
			.orElseThrow(() -> new MainTravelException(MainTravelExceptionType.NOT_FOUND));
		return MainTravelDto.from(mainTravel);
	}

	@Override
	public Long saveMainTravel(MainTravelSaveDto saveDto) {
		MainTravel save = mainTravelRepository.save(MainTravel.builder()
			.title(saveDto.title())
			.startDate(saveDto.startDate())
			.endDate(saveDto.endDate())
			.isCompleted(saveDto.isCompleted())
			.isVisible(saveDto.isVisible())
			.build());
		return save.getId();
	}

	@Override
	public Long updateMainTravel(MainTravelUpdateDto updateDto) {
		MainTravel mainTravel = mainTravelRepository.findById(updateDto.id())
			.orElseThrow(() -> new MainTravelException(MainTravelExceptionType.NOT_FOUND));
		mainTravel.update(updateDto.title(), updateDto.startDate(), updateDto.endDate(), updateDto.isVisible(),
			updateDto.isCompleted());
		return mainTravel.getId();
	}

	@Override
	public void deleteMainTravel(Long mainTravelId) {
		MainTravel mainTravel = mainTravelRepository.findById(mainTravelId).orElseThrow();
		mainTravelRepository.delete(mainTravel);
	}
}
