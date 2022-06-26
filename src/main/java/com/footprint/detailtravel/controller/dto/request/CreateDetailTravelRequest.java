package com.footprint.detailtravel.controller.dto.request;

import static java.time.LocalDate.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.footprint.detailtravel.domain.Address;
import com.footprint.detailtravel.service.dto.create.DetailTravelSaveDto;

/**
 * Created by ShinD on 2022/06/23.
 */
public record CreateDetailTravelRequest(String title,
										String review,
										String tip,
										String visitedDate,
										Address address,
										List<CreatePriceRequest> createPriceRequests,
										List<CreateImageRequest> createImageRequests) {

	public DetailTravelSaveDto toServiceDto() {
		return new DetailTravelSaveDto(
			title(),
			review(),
			tip(),
			parse(visitedDate(), DateTimeFormatter.ISO_LOCAL_DATE),
			address(),
			createPriceRequests().stream().map(CreatePriceRequest::toServiceDto).toList(),
			createImageRequests().stream().map(CreateImageRequest::toServiceDto).toList());
	}
}