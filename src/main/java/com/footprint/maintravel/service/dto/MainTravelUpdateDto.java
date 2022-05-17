package com.footprint.maintravel.service.dto;

import java.time.LocalDate;

public record MainTravelUpdateDto(
	Long id,
	Boolean isCompleted,
	String title,
	LocalDate startDate,
	LocalDate endDate,
	Boolean isVisible
) {

}
