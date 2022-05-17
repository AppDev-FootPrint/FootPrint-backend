package com.footprint.maintravel.controller.dto;

import com.footprint.maintravel.service.dto.MainTravelDto;

public record MainTravelResponse(
	Long id,
	String title,
	String startDate,
	String endDate,
	Boolean isVisited,
	Integer likeNum
) {
	public static MainTravelResponse from(MainTravelDto mainTravelDto) {
		return new MainTravelResponse(
			mainTravelDto.id(),
			mainTravelDto.title(),
			mainTravelDto.startDate(),
			mainTravelDto.endDate(),
			mainTravelDto.isVisited(),
			mainTravelDto.likeNum());
	}
}
