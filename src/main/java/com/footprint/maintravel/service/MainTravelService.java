package com.footprint.maintravel.service;

import com.footprint.maintravel.service.dto.info.MainTravelInfoDto;
import com.footprint.maintravel.service.dto.save.MainTravelSaveDto;
import com.footprint.maintravel.service.dto.update.MainTravelUpdateDto;

public interface MainTravelService {

	Long saveMainTravel(Long memberId, MainTravelSaveDto saveDto);

	Long updateMainTravel(Long memberId, MainTravelUpdateDto updateDto);

	MainTravelInfoDto getMainTravel(Long memberId, Long travelId);

	void deleteMainTravel(Long memberId, Long mainTravelId);
}
