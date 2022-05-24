package com.footprint.maintravel.service.dto;

import java.time.format.DateTimeFormatter;

import com.footprint.maintravel.domain.MainTravel;

public record MainTravelDto(
	Long id,
	String title,
	String startDate,
	String endDate,
	String createdAt,
	Boolean isVisible,
	Boolean isCompleted,
	Integer likeNum
) {
	public static MainTravelDto from(MainTravel mainTravel) {
		return new MainTravelDto(
			mainTravel.getId(),
			mainTravel.getTitle(),
			mainTravel.getStartDate().toString(),
			mainTravel.getEndDate().toString(),
			mainTravel.getCreatedAt().toString(),
			mainTravel.getIsVisible(),
			mainTravel.isCompleted(),
			mainTravel.getLikeNum()
		);
	}
}
