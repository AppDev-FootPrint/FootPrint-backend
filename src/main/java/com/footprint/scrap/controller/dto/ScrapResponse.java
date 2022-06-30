package com.footprint.scrap.controller.dto;

import com.footprint.maintravel.controller.dto.MainTravelListResponse;
import com.footprint.scrap.service.dto.ScrapDto;

public record ScrapResponse(Long scrapId, MainTravelListResponse mainTravelListResponse) {

	public static ScrapResponse from(ScrapDto scrapDto) {
		return new ScrapResponse(scrapDto.scrapId(), MainTravelListResponse.from(scrapDto.mainTravelListDto()));
	}
}
