package com.footprint.detailtravel.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.footprint.detailtravel.domain.DetailTravel;
import com.footprint.detailtravel.exception.DetailTravelException;
import com.footprint.detailtravel.exception.DetailTravelExceptionType;
import com.footprint.detailtravel.repository.DetailTravelRepository;
import com.footprint.detailtravel.service.dto.DetailTravelDto;
import com.footprint.detailtravel.service.dto.DetailTravelListDto;
import com.footprint.detailtravel.service.dto.UpdateDetailTravelDto;
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


	//TODO Price 랑 Image 구현 후 추가




	@Override
	public void update(Long memberId, UpdateDetailTravelDto updateDto) {

		DetailTravel detailTravel = getDetailTravel(updateDto.detailTravelId());

		/*
		 * DetailTravel 이 속한 mainTravel 의 작성자 id 와 DetailTravel 수정 요청을 보낸 사람의 아이디가 같은지 확인
		 * 같지 않다면 예외 발생
		 */
		//TODO 쿼리가 getWriter 에서 1번 더 추가로 발생할 것 같은 예상. 확인 후 수정하여 성능 향상하기
		checkAuthority(memberId, detailTravel.getMainTravel().getWriter().getId());


		//TODO price 랑 dto 추가
		detailTravel.update(updateDto.title(), updateDto.review(), updateDto.tip(), updateDto.visitedDate(), updateDto.address());
	}




	@Override
	public void delete(Long memberId, Long detailTravelId) {
		DetailTravel detailTravel = getDetailTravel(detailTravelId);

		checkAuthority(memberId, detailTravel.getMainTravel().getWriter().getId());

		detailTravelRepository.delete(detailTravel);
	}




	@Override
	@Transactional(readOnly = true)
	public DetailTravelDto getById(Long detailTravelId) {

		//TODO 성능문제 발생, DetailTravelId를 통해 Price 와 Image 쿼리 따로 작성하여 받아오기
		/*
		* priceRepository.findAllByDetailTravelId(detailTravelId);
		* imageRepository.findAllByDetailTravelId(detailTravelId);
		*/
		DetailTravel detailTravel = getDetailTravel(detailTravelId);

		return DetailTravelDto.from(detailTravel);
	}



	@Override
	@Transactional(readOnly = true)
	public DetailTravelListDto getAllByMainTravelId(Long mainTravelId) {
		return DetailTravelListDto.from(detailTravelRepository.findAllByMainTravelId(mainTravelId));
	}







	//== private class ==//

	private void checkAuthority(Long requesterId, Long ownerId) {
		if(!requesterId.equals(ownerId))
			throw new DetailTravelException(DetailTravelExceptionType.NO_AUTHORITY);
	}


	private DetailTravel getDetailTravel(Long detailTravelId) {
		return detailTravelRepository.findById(detailTravelId)
			.orElseThrow(() -> new DetailTravelException(DetailTravelExceptionType.NOT_FOUND));
	}
}
