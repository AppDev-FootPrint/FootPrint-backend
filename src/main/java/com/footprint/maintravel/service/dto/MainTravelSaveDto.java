package com.footprint.maintravel.service.dto;

import java.time.LocalDate;

import com.footprint.maintravel.domain.MainTravel;

public record MainTravelSaveDto(
	Boolean isCompleted,
	String title,
	LocalDate startDate,
	LocalDate endDate,
	Boolean isVisible) {

	public MainTravel toEntity() {
		return MainTravel.builder()
			.title(title())
			.startDate(startDate())
			.endDate(endDate())
			.isCompleted(isCompleted())
			.isVisible(isVisible())
			.build();
	}
}
