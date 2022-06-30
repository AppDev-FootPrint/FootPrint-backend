package com.footprint.detailtravel.controller.dto.response;

import java.util.List;

import com.footprint.detailtravel.domain.Address;
import com.footprint.detailtravel.service.dto.info.DetailTravelDto;
import com.footprint.image.controller.dto.ImageInfoResponse;
import com.footprint.price.controller.dto.PriceInfoResponse;

/**
 * Created by ShinD on 2022/06/25.
 */
public record DetailTravelInfoResponse(Long id,
									   Long mainTravelId,
									   String title,
									   String review,
									   String tip,
									   String visitedDate,
									   Address address,
									   String createdAt,
									   List<PriceInfoResponse> priceInfoResponseList,
									   List<ImageInfoResponse> imageInfoResponseList) {



	public static DetailTravelInfoResponse from(DetailTravelDto detailTravelDto) {
		return new DetailTravelInfoResponse(
			detailTravelDto.detailTravelId(),
			detailTravelDto.mainTravelId(),
			detailTravelDto.title(),
			detailTravelDto.review(),
			detailTravelDto.tip(),
			detailTravelDto.visitedDate().toString(),
			detailTravelDto.address(),
			detailTravelDto.createdAt().toString(),
			detailTravelDto.priceDtoList().stream().map(PriceInfoResponse::from).toList(),
			detailTravelDto.imageDtoList().stream().map(ImageInfoResponse::from).toList()
		);
	}
}
