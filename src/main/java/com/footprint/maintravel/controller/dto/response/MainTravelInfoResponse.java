package com.footprint.maintravel.controller.dto.response;

import com.footprint.detailtravel.controller.dto.response.SimpleDetailTravelListResponse;
import com.footprint.maintravel.service.dto.info.MainTravelInfoDto;
import com.footprint.member.controller.dto.response.MemberInfoResponse;

public record MainTravelInfoResponse(
	Long id,
	MemberInfoResponse writerInfo,
	String title,
	String startDate,
	String endDate,
	String createdAt,
	Boolean isVisible,
	Boolean isCompleted,
	String mainImagePath,
	Integer likeNum,
	SimpleDetailTravelListResponse simpleDetailTravelListResponse
) {
	public static MainTravelInfoResponse from(MainTravelInfoDto mainTravelDto) {
		return new MainTravelInfoResponse(
			mainTravelDto.id(),
			MemberInfoResponse.from(mainTravelDto.writerInfo()),
			mainTravelDto.title(),
			mainTravelDto.startDate(),
			mainTravelDto.endDate(),
			mainTravelDto.createdAt(),
			mainTravelDto.isVisible(),
			mainTravelDto.isCompleted(),
			mainTravelDto.mainImagePath(),
			mainTravelDto.likeNum(),
			SimpleDetailTravelListResponse.from(mainTravelDto.simpleDetailTravelListDto())
		);
	}
}
