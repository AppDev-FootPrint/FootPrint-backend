package com.footprint.maintravel.controller.dto.response;

import com.footprint.maintravel.domain.MainTravel;
import com.footprint.maintravel.service.dto.info.MainTravelSimpleInfoDto;
import com.footprint.member.controller.dto.response.MemberInfoResponse;
import com.footprint.member.service.dto.MemberInfoDto;

/**
 * Created by ShinD on 2022/07/17.
 */
public record MainTravelSimpleInfoResponse(
	MemberInfoResponse writerInfoResponse,
	String title,
	String imagePath,
	int commentCount,
	String createdAt
) {

	public static MainTravelSimpleInfoResponse from(MainTravelSimpleInfoDto mainTravelSimpleInfoDto) {
		return new MainTravelSimpleInfoResponse(
			MemberInfoResponse.from(mainTravelSimpleInfoDto.writerInfoDto()),
			mainTravelSimpleInfoDto.title(),
			mainTravelSimpleInfoDto.imagePath(),
			mainTravelSimpleInfoDto.commentCount(),
			mainTravelSimpleInfoDto.createdAt()
		);
	}
}
