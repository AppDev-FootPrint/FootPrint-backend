package com.footprint.detailtravel.service.dto;

import java.util.ArrayList;
import java.util.List;

import com.footprint.detailtravel.domain.DetailTravel;

/**
 * Created by ShinD on 2022/05/24.
 */
public record DetailTravelListDto(int total, List<DetailTravelDto> detailTravelDtoList) {


	public static DetailTravelListDto from(List<DetailTravel> detailTravelList) {
		List<DetailTravelDto> detailTravelDtoList = detailTravelList.stream().map(DetailTravelDto::from).toList();
		return new DetailTravelListDto(detailTravelDtoList.size(), detailTravelDtoList);
	}
}
