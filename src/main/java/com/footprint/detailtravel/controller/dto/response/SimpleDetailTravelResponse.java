package com.footprint.detailtravel.controller.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.footprint.detailtravel.domain.Address;
import com.footprint.detailtravel.service.dto.info.SimpleDetailTravelDto;

/**
 * Created by ShinD on 2022/06/25.
 */
public record SimpleDetailTravelResponse(Long detailTravelId,
										 Long mainTravelId,
										 String title,
										 String review,
										 String visitedDate,
										 Address address,
										 String createdAt) {

	public static SimpleDetailTravelResponse from(SimpleDetailTravelDto simpleDetailTravelDto) {
		return new SimpleDetailTravelResponse(
			simpleDetailTravelDto.detailTravelId(),
			simpleDetailTravelDto.mainTravelId(),
			simpleDetailTravelDto.title(),
			simpleDetailTravelDto.review(),
			simpleDetailTravelDto.visitedDate().toString(),
			simpleDetailTravelDto.address(),
			simpleDetailTravelDto.createdAt().toString()
		);
	}
}
