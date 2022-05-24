package com.footprint.detailtravel.service.dto;

import java.time.LocalDate;

import com.footprint.detailtravel.domain.Address;

/**
 * Created by ShinD on 2022/05/24.
 */
public record UpdateDetailTravelDto(Long detailTravelId,
									Long mainTravelId,
									String title,
									String review,
									String tip,
									LocalDate visitedDate,
									Address address) {
	//TODO Price랑 Image 추가

}
