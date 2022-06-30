package com.footprint.detailtravel.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.footprint.detailtravel.domain.DetailTravel;
import com.footprint.detailtravel.exception.DetailTravelException;
import com.footprint.detailtravel.exception.DetailTravelExceptionType;
import com.footprint.detailtravel.repository.DetailTravelRepository;
import com.footprint.detailtravel.service.dto.info.DetailTravelDto;
import com.footprint.detailtravel.service.dto.info.ImageDto;
import com.footprint.detailtravel.service.dto.info.PriceDto;
import com.footprint.maintravel.domain.MainTravel;
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
	public DetailTravelDto getById(Long memberId, Long detailTravelId) {

		DetailTravel detailTravel = detailTravelRepository.findWithMainTravelAndWriterById(detailTravelId)
			.orElseThrow(() -> new DetailTravelException(DetailTravelExceptionType.NOT_FOUND));

		MainTravel mainTravel = detailTravel.getMainTravel();

		//== 미완성이거나 private 게시물인 경우에는 작성자가 아니면 조회 불가 ==//
		if( !mainTravel.isVisible() || !mainTravel.isCompleted() ) {
			checkAuthority(memberId, mainTravel.getWriter().getId());
			//TODO 권한 없을때 NULL을 반환할 지, 권한이 없다는 예외 메세지를 출력할 지 고민
		}


		List<PriceDto> priceDtoList = detailTravel.getPrices().stream().map(PriceDto::from).toList();
		List<ImageDto> imageDtoList = detailTravel.getImages().stream().map(ImageDto::from).toList();

		return DetailTravelDto.of(detailTravel, priceDtoList, imageDtoList);
	}



	private void checkAuthority(Long requesterId, Long ownerId) {
		if(!requesterId.equals(ownerId))
			throw new DetailTravelException(DetailTravelExceptionType.NO_AUTHORITY);
	}


}
