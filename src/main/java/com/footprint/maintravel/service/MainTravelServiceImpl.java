package com.footprint.maintravel.service;

import static com.footprint.maintravel.exception.MainTravelExceptionType.*;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.footprint.detailtravel.domain.DetailTravel;
import com.footprint.detailtravel.repository.DetailTravelRepository;
import com.footprint.detailtravel.service.dto.create.DetailTravelSaveDto;
import com.footprint.detailtravel.service.dto.info.SimpleDetailTravelListDto;
import com.footprint.maintravel.domain.MainTravel;
import com.footprint.maintravel.exception.MainTravelException;
import com.footprint.maintravel.exception.MainTravelExceptionType;
import com.footprint.maintravel.repository.MainTravelRepository;
import com.footprint.maintravel.service.dto.info.MainTravelInfoDto;
import com.footprint.maintravel.service.dto.save.MainTravelSaveDto;
import com.footprint.maintravel.service.dto.update.MainTravelUpdateDto;
import com.footprint.member.domain.Member;
import com.footprint.member.exception.MemberException;
import com.footprint.member.exception.MemberExceptionType;
import com.footprint.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class MainTravelServiceImpl implements MainTravelService {

	private final MainTravelRepository mainTravelRepository;
	private final DetailTravelRepository detailTravelRepository;
	private final MemberRepository memberRepository;


	@Override
	@Transactional(readOnly = true)
	public MainTravelInfoDto getMainTravel(Long memberId, Long travelId) {

		MainTravel mainTravel = mainTravelRepository.findWithWriterById(travelId)
													.orElseThrow(() -> new MainTravelException(NOT_FOUND));


		//== 미완성이거나 private 게시물인 경우에는 작성자가 아니면 조회 불가 ==//
		if( !mainTravel.isVisible() || !mainTravel.isCompleted() ) {
			checkAuthority(memberId, mainTravel.getWriter().getId());
			//TODO 권한 없을때 NULL을 반환할 지, 권한이 없다는 예외 메세지를 출력할 지 고민
		}

		List<DetailTravel> detailTravelList = detailTravelRepository.findAllByMainTravelId(travelId);
		//TODO 쿼리 얼마나 나가는지 측정하기


		return MainTravelInfoDto.from(mainTravel, SimpleDetailTravelListDto.from(detailTravelList));
	}





	/**
	 * Main Travel 을 저장하면 자연스레 Detail Travel에 대한 정보도 들어옴, 따라서 모두 저장
	 */
	@Override
	public Long saveMainTravel(Long memberId, MainTravelSaveDto saveDto) {
		Member member = memberRepository.findById(memberId)
			.orElseThrow(() -> new MemberException(MemberExceptionType.NOT_FOUND));


		MainTravel mainTravel = saveDto.toEntity();

		mainTravel.setWriter(member);

		return mainTravelRepository.save(mainTravel).getId();
	}






	/**
	 * Main Travel 을 수정하면 자연스레 Detail Travel에 대한 정보도 수정, 따라서 모두 수정
	 */
	@Override
	public Long updateMainTravel(Long memberId, MainTravelUpdateDto updateDto) {

		MainTravel mainTravel = mainTravelRepository.findById(updateDto.id())
													.orElseThrow(() -> new MainTravelException(NOT_FOUND));

		checkAuthority(memberId, mainTravel.getWriter().getId());

		mainTravel.update(updateDto.title(),
						  updateDto.startDate(),
						  updateDto.endDate(),
						  updateDto.isVisible(),
						  updateDto.isCompleted(),
						  updateDto.detailTravelSaveDtoList().stream().map(DetailTravelSaveDto::toEntity).toList());


		return mainTravel.getId();
	}


	/**
	 * Main Travel 을 삭제하면 자연스레 Detail Travel에 대한 정보도 삭제, 즉 모두 삭제
	 */
	@Override
	public void deleteMainTravel(Long memberId, Long mainTravelId) {
		MainTravel mainTravel = mainTravelRepository.findById(mainTravelId)
													.orElseThrow(() -> new MainTravelException(NOT_FOUND));

		checkAuthority(memberId, mainTravel.getWriter().getId());


		mainTravelRepository.delete(mainTravel);
	}






	private void checkAuthority(Long requesterId, Long ownerId) {
		if(!requesterId.equals(ownerId))
			throw new MainTravelException(MainTravelExceptionType.NO_AUTHORITY);
	}
}
