package com.footprint.detailtravel.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.footprint.detailtravel.domain.DetailTravel;
import com.footprint.detailtravel.exception.DetailTravelException;
import com.footprint.detailtravel.exception.DetailTravelExceptionType;
import com.footprint.detailtravel.repository.DetailTravelRepository;
import com.footprint.detailtravel.service.dto.create.ImageDto;
import com.footprint.detailtravel.service.dto.create.PriceDto;
import com.footprint.detailtravel.service.dto.info.DetailTravelDto;
import com.footprint.maintravel.repository.MainTravelRepository;
import com.footprint.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

/**
 * Created by ShinD on 2022/05/24.
 */
@Service
@Transactional
@RequiredArgsConstructor
public class DetailTravelServiceImpl implements DetailTravelService{

	private final DetailTravelRepository detailTravelRepository;
	private final MainTravelRepository mainTravelRepository;
	private final MemberRepository memberRepository;




	@Override
	@Transactional(readOnly = true)
	public DetailTravelDto getById(Long detailTravelId) {

		DetailTravel detailTravel = detailTravelRepository.findById(detailTravelId)
			.orElseThrow(() -> new DetailTravelException(DetailTravelExceptionType.NOT_FOUND));

		List<PriceDto> priceDtoList = detailTravel.getPrices().stream().map(PriceDto::from).toList();
		List<ImageDto> imageDtoList = detailTravel.getImages().stream().map(ImageDto::from).toList();

		return DetailTravelDto.of(detailTravel, priceDtoList, imageDtoList);
	}




}
