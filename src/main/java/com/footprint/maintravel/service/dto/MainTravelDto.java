package com.footprint.maintravel.service.dto;

import com.footprint.maintravel.domain.MainTravel;

public record MainTravelDto(
	Long id,
	String title,
	String startDate,
	String endDate,
	Boolean isVisible,
	Integer likeNum
) {
	public static MainTravelDto from(MainTravel mainTravel) {
		return new MainTravelDto(
			mainTravel.getId(),
			mainTravel.getTitle(),
			mainTravel.getStartDate().toString(),
			mainTravel.getEndDate().toString(),
			mainTravel.getIsVisible(),
			mainTravel.getLikeNum()
		);
	}
}
