package com.footprint.maintravel.service;

import com.footprint.maintravel.service.dto.MainTravelDto;
import com.footprint.maintravel.service.dto.MainTravelSaveDto;
import com.footprint.maintravel.service.dto.MainTravelUpdateDto;

public interface MainTravelService {

	Long saveMainTravel(MainTravelSaveDto saveDto);

	Long updateMainTravel(MainTravelUpdateDto updateDto);

	MainTravelDto getMainTravel(Long travelId);

	void deleteMainTravel(Long mainTravelId);
}
