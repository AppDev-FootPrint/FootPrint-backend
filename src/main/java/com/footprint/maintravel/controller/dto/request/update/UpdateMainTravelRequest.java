package com.footprint.maintravel.controller.dto.request.update;

import static java.time.LocalDate.*;

import java.time.format.DateTimeFormatter;
import java.util.List;

import com.footprint.detailtravel.controller.dto.request.CreateDetailTravelRequest;
import com.footprint.detailtravel.controller.dto.request.CreateImageRequest;
import com.footprint.maintravel.service.dto.update.MainTravelUpdateDto;

public record UpdateMainTravelRequest(String title,
									  String startDate,
									  String endDate,
									  Boolean isVisible,
									  Boolean isCompleted,
									  CreateImageRequest mainImage,
									  List<CreateDetailTravelRequest> createDetailTravelRequest) {

	public MainTravelUpdateDto toServiceDto(Long mainTravelId) {
		return new MainTravelUpdateDto(
			mainTravelId,
			title(),
			parse(startDate(), DateTimeFormatter.ISO_LOCAL_DATE),
			parse(endDate(), DateTimeFormatter.ISO_LOCAL_DATE),
			isVisible(),
			isCompleted(),
			mainImage().toServiceDto(),
			createDetailTravelRequest().stream().map(CreateDetailTravelRequest::toServiceDto).toList());
	}







}



