package com.footprint.scrap.service.dto;

import com.footprint.maintravel.service.dto.MainTravelListDto;
import com.footprint.scrap.domain.Scrap;

public record ScrapDto(Long scrapId, MainTravelListDto mainTravelListDto) {

	public static ScrapDto from(Scrap scrap) {
		return new ScrapDto(scrap.getId(), MainTravelListDto.from(scrap.getMainTravel()));
	}
}
