package com.footprint.detailtravel.service.dto.info;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.footprint.detailtravel.domain.Address;
import com.footprint.detailtravel.domain.DetailTravel;

/**
 * Created by ShinD on 2022/06/24.
 */
public record SimpleDetailTravelDto(Long detailTravelId,
									String title,
									String review,
									LocalDate visitedDate,
									Address address,
									LocalDateTime createdAt) {

	public static SimpleDetailTravelDto from(DetailTravel detailTravel) {
		return new SimpleDetailTravelDto(
			detailTravel.getId(),
			detailTravel.getTitle(),
			detailTravel.getReview(),
			detailTravel.getVisitedDate(),
			detailTravel.getAddress(),
			detailTravel.getCreatedAt());
	}
}
