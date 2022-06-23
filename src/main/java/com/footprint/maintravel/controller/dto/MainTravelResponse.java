// package com.footprint.maintravel.controller.dto;
//
// import com.footprint.maintravel.service.dto.info.MainTravelDto;
//
// public record MainTravelResponse(
// 	Long id,
// 	String title,
// 	String startDate,
// 	String endDate,
// 	String createdAt,
// 	Boolean isVisible,
// 	Boolean isCompleted,
// 	Integer likeNum
// ) {
// 	public static MainTravelResponse from(MainTravelDto mainTravelDto) {
// 		return new MainTravelResponse(
// 			mainTravelDto.id(),
// 			mainTravelDto.title(),
// 			mainTravelDto.startDate(),
// 			mainTravelDto.endDate(),
// 			mainTravelDto.createdAt(),
// 			mainTravelDto.isVisible(),
// 			mainTravelDto.isCompleted(),
// 			mainTravelDto.likeNum());
// 	}
// }
