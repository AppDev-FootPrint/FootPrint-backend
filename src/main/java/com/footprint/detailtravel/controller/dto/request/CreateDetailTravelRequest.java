package com.footprint.detailtravel.controller.dto.request;

import static java.time.LocalDate.*;


import java.time.format.DateTimeFormatter;
import java.util.List;

import com.footprint.detailtravel.domain.Address;
import com.footprint.detailtravel.service.dto.create.DetailTravelSaveDto;

import com.footprint.image.controller.dto.CreateImageRequest;
import com.footprint.price.controller.dto.CreatePriceRequest;

/**
 * Created by ShinD on 2022/06/23.
 */
public record CreateDetailTravelRequest(String title,
										String review,
										String tip,
										String visitedDate,
										Address address,
										List<CreatePriceRequest> createPriceRequestList,
										List<CreateImageRequest> createImageRequestList) {

	public DetailTravelSaveDto toServiceDto() {
		return new DetailTravelSaveDto(
			title(),
			review(),
			tip(),
			parse(visitedDate(), DateTimeFormatter.ISO_LOCAL_DATE),
			address(),
			createPriceRequestList().stream().map(CreatePriceRequest::toServiceDto).toList(),
			createImageRequestList().stream().map(CreateImageRequest::toServiceDto).toList()
			);
	}
}
