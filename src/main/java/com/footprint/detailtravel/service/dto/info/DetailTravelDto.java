package com.footprint.detailtravel.service.dto.info;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.footprint.detailtravel.domain.Address;
import com.footprint.detailtravel.domain.DetailTravel;

import com.footprint.image.service.dto.ImageDto;
import com.footprint.price.service.dto.PriceDto;


/**
 * Created by ShinD on 2022/05/24.
 */
public record DetailTravelDto(Long detailTravelId,
							  Long mainTravelId,
							  String title,
							  String review,
							  String tip,
							  LocalDate visitedDate,
							  Address address,
							  LocalDateTime createdAt,
							  List<PriceDto> priceDtoList,
							  List<ImageDto> imageDtoList) {

	public static DetailTravelDto of(DetailTravel detailTravel, List<PriceDto> priceDtoList, List<ImageDto> imageDtoList) {

		return new DetailTravelDto(
			detailTravel.getId(),
			detailTravel.getMainTravel().getId(),
			detailTravel.getTitle(),
			detailTravel.getReview(),
			detailTravel.getTip(),
			detailTravel.getVisitedDate(),
			detailTravel.getAddress(),
			detailTravel.getCreatedAt(),
			priceDtoList,
			imageDtoList);
	}
}
