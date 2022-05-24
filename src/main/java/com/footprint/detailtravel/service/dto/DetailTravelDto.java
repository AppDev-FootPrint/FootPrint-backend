package com.footprint.detailtravel.service.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.footprint.detailtravel.domain.Address;
import com.footprint.detailtravel.domain.DetailTravel;

/**
 * Created by ShinD on 2022/05/24.
 */
public class DetailTravelDto {

	private Long detailTravelId;
	private String title;
	private String review;
	private String tip;
	private LocalDate visitedDate;
	private Address address;
	private LocalDateTime createdAt;
	private List<PriceDto> priceDtoList = new ArrayList<>();
	private List<ImageDto> imageDtoList = new ArrayList<>();

	public DetailTravelDto(Long detailTravelId, String title, String review, String tip, LocalDate visitedDate,
							Address address, LocalDateTime createdAt, List<PriceDto> priceDtoList, List<ImageDto> imageDtoList) {
		this.detailTravelId = detailTravelId;
		this.title = title;
		this.review = review;
		this.tip = tip;
		this.visitedDate = visitedDate;
		this.address = address;
		this.createdAt = createdAt;
		this.priceDtoList = priceDtoList;
		this.imageDtoList = imageDtoList;
	}



	public static DetailTravelDto from(DetailTravel detailTravel) {

		List<PriceDto> priceDtoList = detailTravel.getPrices().stream().map(PriceDto::from).toList();
		List<ImageDto> imageDtoList = detailTravel.getImages().stream().map(ImageDto::from).toList();

		return new DetailTravelDto(
			detailTravel.getId(),
			detailTravel.getTitle(),
			detailTravel.getReview(),
			detailTravel.getTip(),
			detailTravel.getVisitedDate(),
			detailTravel.getAddress(),
			detailTravel.getCreatedAt(),
			priceDtoList,
			imageDtoList);
	}
}
