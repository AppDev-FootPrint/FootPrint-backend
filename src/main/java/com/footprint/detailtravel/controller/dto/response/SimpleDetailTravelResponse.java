package com.footprint.detailtravel.controller.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.footprint.detailtravel.domain.Address;
import com.footprint.detailtravel.service.dto.info.SimpleDetailTravelDto;

/**
 * Created by ShinD on 2022/06/25.
 */
public record SimpleDetailTravelResponse(Long detailTravelId,
										 String title,
										 String review,
										 LocalDate visitedDate,
										 Address address,
										 LocalDateTime createdAt) {

	public static SimpleDetailTravelResponse from(SimpleDetailTravelDto simpleDetailTravelDto) {
		return new SimpleDetailTravelResponse(
			simpleDetailTravelDto.detailTravelId(),
			simpleDetailTravelDto.title(),
			simpleDetailTravelDto.review(),
			simpleDetailTravelDto.visitedDate(),
			simpleDetailTravelDto.address(),
			simpleDetailTravelDto.createdAt()
		);
	}
}
