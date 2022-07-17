package com.footprint.maintravel.service;

import static com.footprint.maintravel.exception.MainTravelExceptionType.*;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.footprint.detailtravel.domain.DetailTravel;
import com.footprint.detailtravel.repository.DetailTravelRepository;
import com.footprint.detailtravel.service.dto.create.DetailTravelSaveDto;
import com.footprint.detailtravel.service.dto.info.SimpleDetailTravelListDto;
import com.footprint.image.domain.Image;
import com.footprint.image.repository.ImageRepository;

import com.footprint.maintravel.domain.MainTravel;
import com.footprint.maintravel.exception.MainTravelException;
import com.footprint.maintravel.exception.MainTravelExceptionType;
import com.footprint.maintravel.repository.MainTravelRepository;
import com.footprint.maintravel.service.dto.info.MainTravelInfoDto;
import com.footprint.maintravel.service.dto.info.MainTravelInfoListDto;
import com.footprint.maintravel.service.dto.save.MainTravelSaveDto;
import com.footprint.maintravel.service.dto.update.MainTravelUpdateDto;
import com.footprint.member.domain.Member;
import com.footprint.member.exception.MemberException;
import com.footprint.member.exception.MemberExceptionType;
import com.footprint.member.repository.MemberRepository;
import com.footprint.price.domain.Price;
import com.footprint.price.repository.PriceRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class MainTravelServiceImpl implements MainTravelService {


	private final MainTravelRepository mainTravelRepository;
	private final DetailTravelRepository detailTravelRepository;
	private final MemberRepository memberRepository;
	private final PriceRepository priceRepository;
	private final ImageRepository imageRepository;



	@Override
	@Transactional(readOnly = true)
	public MainTravelInfoDto getMainTravel(Long memberId, Long travelId) {

		MainTravel mainTravel = mainTravelRepository.findWithWriterAndImageById(travelId)
													.orElseThrow(() -> new MainTravelException(NOT_FOUND));


		//== 미완성이거나 private 게시물인 경우에는 작성자가 아니면 조회 불가 ==//
		if( !mainTravel.isVisible() || !mainTravel.isCompleted() ) {
			checkAuthority(memberId, mainTravel.getWriter().getId());
			//TODO 권한 없을때 NULL을 반환할 지, 권한이 없다는 예외 메세지를 출력할 지 고민
		}



		return MainTravelInfoDto.from(mainTravel, SimpleDetailTravelListDto.from(mainTravel.getDetailTravels()));
	}

	@Override
	public MainTravelInfoListDto getMainTravelList(Long memberId) {
		Member member = memberRepository.findById(memberId)
			.orElseThrow(() -> new MemberException(MemberExceptionType.NOT_FOUND));

		List<MainTravel> all = mainTravelRepository.findAll();
		List<MainTravel> mine = mainTravelRepository.findAllByWriter(member);

		all.removeAll(mine);//내거 지우고

		List<MainTravel> mainTravels = all.stream()//내게 지워진 전체 list 중 전체공개될 수 있는 게시물들만 걸러냄
			.filter(MainTravel::isVisible)
			.filter(MainTravel::isCompleted)
			.collect(Collectors.toList());

		List<MainTravel> myCompleteList = mine.stream().filter(MainTravel::isCompleted).collect(Collectors.toList());//내거에서 완성된것만

		mainTravels.addAll(myCompleteList);

		mainTravels.sort((o1,  o2) ->o2.getCreatedAt().compareTo(o1.getCreatedAt()));//최근에 생성된 것부터 차례대로

		return MainTravelInfoListDto.from(mainTravels);
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

		deleteAllDetailTravelIn(mainTravel);

		mainTravel.update(updateDto.title(),
						  updateDto.startDate(),
						  updateDto.endDate(),
						  updateDto.isVisible(),
						  updateDto.isCompleted(),
						  updateDto.detailTravelSaveDtoList().stream().map(DetailTravelSaveDto::toEntity).toList());


		return mainTravel.getId();
	}



	private void deleteAllDetailTravelIn(MainTravel mainTravel) {
		List<DetailTravel> detailTravelList = mainTravel.getDetailTravels();
		//반드시 price를 삭제한 이후 detail을 삭제해야 함
		priceRepository.deleteAllByIdInBatch(detailTravelList.stream().flatMap(dt -> dt.getPrices().stream().map(Price::getId)).toList());
		imageRepository.deleteAllByIdInBatch(detailTravelList.stream().flatMap(dt -> dt.getImages().stream().map(Image::getId)).toList());
		detailTravelRepository.deleteAllByIdInBatch(detailTravelList.stream().map(DetailTravel::getId).toList());
	}





	/**
	 * Main Travel 을 삭제하면 자연스레 Detail Travel에 대한 정보도 삭제, 즉 모두 삭제
	 */
	@Override
	public void deleteMainTravel(Long memberId, Long mainTravelId) {
		MainTravel mainTravel = mainTravelRepository.findById(mainTravelId)
													.orElseThrow(() -> new MainTravelException(NOT_FOUND));

		checkAuthority(memberId, mainTravel.getWriter().getId());


		deleteAllDetailTravelIn(mainTravel);


		mainTravelRepository.delete(mainTravel);
	}






	private void checkAuthority(Long requesterId, Long ownerId) {
		if(!requesterId.equals(ownerId))
			throw new MainTravelException(MainTravelExceptionType.NO_AUTHORITY);
	}
}
