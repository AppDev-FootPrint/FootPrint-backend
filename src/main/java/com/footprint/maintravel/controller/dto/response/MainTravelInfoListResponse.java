package com.footprint.maintravel.controller.dto.response;

import java.util.List;

import com.footprint.maintravel.service.dto.info.MainTravelInfoListDto;
import com.footprint.maintravel.service.dto.info.MainTravelSimpleInfoDto;

/**
 * Created by ShinD on 2022/07/17.
 */
public record MainTravelInfoListResponse(int total, List<MainTravelSimpleInfoResponse> mainTravelSimpleInfoResponses) {
	public static MainTravelInfoListResponse from(MainTravelInfoListDto mainTravelList) {
		List<MainTravelSimpleInfoResponse> mainTravelSIResps =
			mainTravelList.mainTravelSimpleInfoDtos()
			.stream()
			.map(MainTravelSimpleInfoResponse::from)
			.toList();
		return new MainTravelInfoListResponse(mainTravelList.total(), mainTravelSIResps);
	}
}
