package com.footprint.detailtravel.service.dto.create;

import java.time.LocalDate;
import java.util.List;

import com.footprint.detailtravel.domain.Address;
import com.footprint.detailtravel.domain.DetailTravel;
import com.footprint.image.domain.Image;

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


		detailTravel.setPrices(priceSaveDtoList().stream().map(PriceSaveDto::toEntity).toList());
		detailTravel.setImages(imageSaveDtoList().stream().map(ImageSaveDto::toEntity).toList());

		return detailTravel;
	}
}
