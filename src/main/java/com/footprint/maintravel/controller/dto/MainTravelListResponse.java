package com.footprint.maintravel.controller.dto;

import com.footprint.maintravel.service.dto.MainTravelListDto;

public record MainTravelListResponse(
	Long id,
	String title,
	String imagePath,
	Integer likeNum
) {
	public static MainTravelListResponse from(MainTravelListDto mainTravelListDto) {
		return new MainTravelListResponse(
			mainTravelListDto.id(),
			mainTravelListDto.title(),
			mainTravelListDto.imagePath(),
			mainTravelListDto.likeNum()
		);
	}
}
