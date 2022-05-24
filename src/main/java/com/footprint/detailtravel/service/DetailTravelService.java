package com.footprint.detailtravel.service;

import com.footprint.detailtravel.service.dto.DetailTravelDto;
import com.footprint.detailtravel.service.dto.DetailTravelListDto;
import com.footprint.detailtravel.service.dto.UpdateDetailTravelDto;

/**
 * Created by ShinD on 2022/05/24.
 */
public interface DetailTravelService{
	/**
	 * Detail Travel 수정 기능
	 * Detail Travel 삭제 기능
	 * Detail Travel 단일 조회 기능
	 * Main Travel 에 속한 Detail Travel 조회 기능
	 */

	void update(Long memberId, UpdateDetailTravelDto updateDetailTravelDto);
	void delete(Long memberId, Long detailTravelId);

	DetailTravelDto getById(Long detailTravelId);
	DetailTravelListDto getAllByMainTravelId(Long mainTravelId);
}
