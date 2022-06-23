package com.footprint.detailtravel.service.dto;

import java.time.LocalDate;
import java.util.List;

import com.footprint.detailtravel.domain.Address;
import com.footprint.detailtravel.domain.DetailTravel;
import com.footprint.maintravel.service.dto.save.ImageSaveDto;
import com.footprint.maintravel.service.dto.save.PriceSaveDto;

import lombok.Builder;

/**
 * Created by ShinD on 2022/05/31.
 */
@Builder
public record CreateDetailTravelDto(String title,
									String review,
									String tip,
									LocalDate visitedDate,
									Address address,
									List<PriceSaveDto> createPriceDtos,
									List<ImageSaveDto> createImageDtos) {

	public DetailTravel toEntity() {
		return DetailTravel.builder().title(title).review(review).tip(tip).visitedDate(visitedDate).address(address).build();
	}


}
