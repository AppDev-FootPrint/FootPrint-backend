package com.footprint.maintravel.service.dto.save;

import java.time.LocalDate;
import java.util.List;

import com.footprint.detailtravel.domain.Address;
import com.footprint.detailtravel.domain.DetailTravel;

public record DetailTravelSaveDto(String title,
								  String review,
								  String tip,
								  LocalDate visitedDate,
								  Address address,
								  List<PriceSaveDto> priceSaveDtoList,
								  List<ImageSaveDto> imageSaveDtoList) {


	public DetailTravel toEntity() {
		return null;
	}
}
