package com.footprint.maintravel.service.dto.save;

import java.time.LocalDate;
import java.util.List;

import com.footprint.detailtravel.service.dto.create.DetailTravelSaveDto;
import com.footprint.detailtravel.service.dto.create.ImageSaveDto;
import com.footprint.maintravel.domain.MainTravel;

public record MainTravelSaveDto(
	String title,
	LocalDate startDate,
	LocalDate endDate,
	Boolean isVisible,
	Boolean isCompleted,
	ImageSaveDto imageSaveDto,
	List<DetailTravelSaveDto> detailTravelSaveDtoList) {


	public MainTravel toEntity() {
		MainTravel mainTravel = MainTravel.builder()
			.title(title())
			.startDate(startDate())
			.endDate(endDate())
			.isCompleted(isCompleted())
			.isVisible(isVisible())
			.build();

		mainTravel.setDetailTravels(detailTravelSaveDtoList.stream().map(DetailTravelSaveDto::toEntity).toList());


		return mainTravel;
	}


}
