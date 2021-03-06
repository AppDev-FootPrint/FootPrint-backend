package com.footprint.maintravel.controller.dto.request.create;

import static java.time.LocalDate.*;

import java.time.format.DateTimeFormatter;
import java.util.List;

import com.footprint.detailtravel.controller.dto.request.CreateDetailTravelRequest;
import com.footprint.maintravel.service.dto.save.MainTravelSaveDto;



public record CreateMainTravelRequest(String title,
									  String startDate,
									  String endDate,
									  Boolean isVisible,
									  Boolean isCompleted,
									  String mainImagePath,
									  List<CreateDetailTravelRequest> createDetailTravelRequest) {

	public MainTravelSaveDto toServiceDto() {
		return new MainTravelSaveDto(
			title(),
			parse(startDate(), DateTimeFormatter.ISO_LOCAL_DATE),
			parse(endDate(), DateTimeFormatter.ISO_LOCAL_DATE),
			isVisible(),
			isCompleted(),
			mainImagePath(),
			createDetailTravelRequest().stream().map(CreateDetailTravelRequest::toServiceDto).toList());
	}
}



