package com.footprint.maintravel.controller.dto.request.create;

import java.time.LocalDate;
import java.util.List;

import com.footprint.detailtravel.domain.Address;
import com.footprint.maintravel.service.dto.save.DetailTravelSaveDto;

/**
 * Created by ShinD on 2022/06/23.
 */
public record CreateDetailTravelRequest(String title,
										String review,
										String tip,
										LocalDate visitedDate,
										Address address,
										List<CreatePriceRequest> createPriceRequests,
										List<CreateImageRequest> createImageRequests) {

	public DetailTravelSaveDto toServiceDto() {
		return new DetailTravelSaveDto(
			title(),
			review(),
			tip(),
			visitedDate(),
			address(),
			createPriceRequests().stream().map(CreatePriceRequest::toServiceDto).toList(),
			createImageRequests().stream().map(CreateImageRequest::toServiceDto).toList());
	}
}
