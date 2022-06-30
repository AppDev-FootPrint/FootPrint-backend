package com.footprint.detailtravel.service.dto.info;

import java.util.List;

import com.footprint.detailtravel.domain.DetailTravel;

/**
 * Created by ShinsD on 2022/05/24.
 */
public record SimpleDetailTravelListDto(int total, List<SimpleDetailTravelDto> detailTravelDtoList) {


	public static SimpleDetailTravelListDto from(List<DetailTravel> detailTravelList) {
		List<SimpleDetailTravelDto> detailTravelDtoList = detailTravelList.stream().map(SimpleDetailTravelDto::from).toList();
		return new SimpleDetailTravelListDto(detailTravelDtoList.size(), detailTravelDtoList);
	}
}
