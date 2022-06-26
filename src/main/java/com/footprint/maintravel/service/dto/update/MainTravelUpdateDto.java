package com.footprint.maintravel.service.dto.update;

import java.time.LocalDate;
import java.util.List;

import com.footprint.detailtravel.service.dto.create.DetailTravelSaveDto;
import com.footprint.detailtravel.service.dto.create.ImageSaveDto;

public record MainTravelUpdateDto(
	Long id,
	String title,
	LocalDate startDate,
	LocalDate endDate,
	Boolean isVisible,
	Boolean isCompleted,
	String mainImagePath,
	List<DetailTravelSaveDto> detailTravelSaveDtoList) {

}
