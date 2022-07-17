package com.footprint.maintravel.service.dto.info;

import java.util.ArrayList;
import java.util.List;

import com.footprint.maintravel.domain.MainTravel;

/**
 * Created by ShinD on 2022/07/17.
 */
public record MainTravelInfoListDto(int total,List<MainTravelSimpleInfoDto> mainTravelSimpleInfoDtos) {

	public static MainTravelInfoListDto from(List<MainTravel> mainTravels) {
		List<MainTravelSimpleInfoDto> mainTravelSimpleInfoDtos = mainTravels.stream()
			.map(MainTravelSimpleInfoDto::from)
			.toList();
		return new MainTravelInfoListDto(mainTravelSimpleInfoDtos.size(), mainTravelSimpleInfoDtos);
	}
}
