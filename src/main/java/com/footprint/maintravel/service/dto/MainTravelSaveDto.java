package com.footprint.maintravel.service.dto;

import java.time.LocalDate;

public record MainTravelSaveDto(
	Boolean isCompleted,
	String title,
	LocalDate startDate,
	LocalDate endDate,
	Boolean isVisible) {
}
