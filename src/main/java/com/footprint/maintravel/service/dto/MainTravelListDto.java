package com.footprint.maintravel.service.dto;

import com.footprint.maintravel.domain.MainTravel;

public record MainTravelListDto(
	Long id,
	String title,
	String imagePath,
	Integer likeNum
) {
	public static MainTravelListDto from(MainTravel mainTravel) {
		return new MainTravelListDto(
			mainTravel.getId(),
			mainTravel.getTitle(),
			mainTravel.getImagePath(),
			mainTravel.getLikeNum()
		);
	}
}
