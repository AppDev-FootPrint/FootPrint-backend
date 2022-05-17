package com.footprint.maintravel.controller.dto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.footprint.maintravel.service.dto.MainTravelSaveDto;
import com.footprint.maintravel.service.dto.MainTravelUpdateDto;

public record MainTravelRequest(
	Boolean isCompleted,
	String title,
	String startDate,
	String endDate,
	Boolean isVisible
) {
	public MainTravelSaveDto toSaveDto() {
		return new MainTravelSaveDto(
			isCompleted(),
			title(),
			LocalDate.parse(startDate(), DateTimeFormatter.ISO_LOCAL_DATE),
			LocalDate.parse(endDate(), DateTimeFormatter.ISO_LOCAL_DATE),
			isVisible());
	}

	public MainTravelUpdateDto toUpdateDto(Long mainTravelId) {
		return new MainTravelUpdateDto(
			mainTravelId,
			isCompleted(),
			title(),
			LocalDate.parse(startDate(), DateTimeFormatter.ISO_LOCAL_DATE),
			LocalDate.parse(endDate(), DateTimeFormatter.ISO_LOCAL_DATE),
			isVisible());
	}
}
