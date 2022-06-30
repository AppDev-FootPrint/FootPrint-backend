package com.footprint.detailtravel.controller.dto.response;

import java.util.List;

import com.footprint.detailtravel.service.dto.info.SimpleDetailTravelListDto;

/**
 * Created by ShinD on 2022/06/25.
 */
public record SimpleDetailTravelListResponse(int total, List<SimpleDetailTravelResponse> detailTravelResponses) {

	public static SimpleDetailTravelListResponse from(SimpleDetailTravelListDto simpleDetailTravelListDto) {

		return new SimpleDetailTravelListResponse(
			simpleDetailTravelListDto.total(),
			simpleDetailTravelListDto.detailTravelDtoList().stream().map(SimpleDetailTravelResponse::from).toList());
	}
}
