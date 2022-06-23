package com.footprint.maintravel.service;

import com.footprint.maintravel.service.dto.info.MainTravelDto;
import com.footprint.maintravel.service.dto.save.MainTravelSaveDto;
import com.footprint.maintravel.service.dto.update.MainTravelUpdateDto;

public interface MainTravelService {

	Long saveMainTravel(Long memberId, MainTravelSaveDto saveDto);

	Long updateMainTravel(Long memberId, MainTravelUpdateDto updateDto);

	MainTravelDto getMainTravel(Long travelId);

	void deleteMainTravel(Long memberId, Long mainTravelId);
}
