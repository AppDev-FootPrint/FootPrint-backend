package com.footprint.maintravel.service.dto.info;

import com.footprint.detailtravel.service.dto.info.SimpleDetailTravelListDto;
import com.footprint.maintravel.domain.MainTravel;
import com.footprint.member.service.dto.MemberInfoDto;

public record MainTravelInfoDto(
	Long id,
	MemberInfoDto writerInfo,
	String title,
	String startDate,
	String endDate,
	String createdAt,
	Boolean isVisible,
	Boolean isCompleted,
	String mainImagePath,
	Integer likeNum,
	SimpleDetailTravelListDto simpleDetailTravelListDto
) {
	public static MainTravelInfoDto from(MainTravel mainTravel, SimpleDetailTravelListDto simpleDetailTravelListDto) {
		 return new MainTravelInfoDto(
			 mainTravel.getId(),
			 MemberInfoDto.from(mainTravel.getWriter()),
		 	 mainTravel.getTitle(),
		 	 mainTravel.getStartDate().toString(),
		 	 mainTravel.getEndDate().toString(),
		 	 mainTravel.getCreatedAt().toString(),
		 	 mainTravel.isVisible(),
		 	 mainTravel.isCompleted(),
			 mainTravel.getImagePath(),
			 mainTravel.getLikeNum(),
			 simpleDetailTravelListDto
		 );
	}
}
