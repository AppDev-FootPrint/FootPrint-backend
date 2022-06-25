package com.footprint.detailtravel.service.dto.create;

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
		DetailTravel detailTravel = DetailTravel.builder()
			.title(title())
			.review(review())
			.tip(tip())
			.visitedDate(visitedDate())
			.address(address())
			.build();
		detailTravel.getPrices().addAll(priceSaveDtoList().stream().map(PriceSaveDto::toEntity).toList());
		detailTravel.getImages().addAll(imageSaveDtoList().stream().map(ImageSaveDto::toEntity).toList());
		return detailTravel;
	}
}
