package com.footprint.maintravel.controller.dto.response;

import com.footprint.maintravel.service.dto.MainTravelListDto;

/**
 * Created by ShinD on 2022/07/17.
 */
public record MainTravelListResponse(
	Long id,
	String title,
	String mainImagePath,
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
