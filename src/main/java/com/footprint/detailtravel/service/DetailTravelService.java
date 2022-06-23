package com.footprint.detailtravel.service;

import com.footprint.detailtravel.service.dto.info.DetailTravelDto;

/**
 * Created by ShinD on 2022/05/24.
 */
public interface DetailTravelService{
	/**
	 * Detail Travel 단일 조회 기능
	 * Main Travel 에 속한 Detail Travel 조회 기능
	 */

	DetailTravelDto getById(Long detailTravelId);
}
