package com.footprint.maintravel.service.dto.info;

import com.footprint.maintravel.domain.MainTravel;
import com.footprint.member.service.dto.MemberInfoDto;

/**
 * Created by ShinD on 2022/07/17.
 */
public record MainTravelSimpleInfoDto(
	Long id,
	MemberInfoDto writerInfoDto,
	String title,
	String imagePath,
	int commentCount,
	String createdAt
) {

	public static MainTravelSimpleInfoDto from(MainTravel mainTravel) {
		return new MainTravelSimpleInfoDto(
			mainTravel.getId(),
			MemberInfoDto.from(mainTravel.getWriter()),
			mainTravel.getTitle(),
			mainTravel.getImagePath(),
			mainTravel.getComments().size(),
			mainTravel.getCreatedAt().toString()
		);
	}
}
