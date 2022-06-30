package com.footprint.detailtravel.service;

import static com.footprint.detailtravel.exception.DetailTravelExceptionType.*;
import static com.footprint.maintravel.fixture.MainTravelFixture.*;
import static com.footprint.member.fixture.MemberFixture.*;
import static com.footprint.util.MappingTestUtil.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;

import com.footprint.auth.service.AuthService;
import com.footprint.detailtravel.domain.DetailTravel;
import com.footprint.detailtravel.exception.DetailTravelException;
import com.footprint.detailtravel.repository.DetailTravelRepository;
import com.footprint.detailtravel.service.dto.create.DetailTravelSaveDto;
import com.footprint.image.service.dto.ImageSaveDto;
import com.footprint.detailtravel.service.dto.info.DetailTravelDto;
import com.footprint.image.service.dto.ImageDto;
import com.footprint.maintravel.exception.MainTravelException;
import com.footprint.maintravel.exception.MainTravelExceptionType;
import com.footprint.maintravel.repository.MainTravelRepository;
import com.footprint.maintravel.service.MainTravelService;
import com.footprint.maintravel.service.dto.save.MainTravelSaveDto;
import com.footprint.member.domain.Member;
import com.footprint.member.repository.MemberRepository;
import com.footprint.price.service.dto.PriceDto;
import com.footprint.price.service.dto.PriceSaveDto;

/**
 * Created by ShinD on 2022/06/26.
 */
@SpringBootTest
@Transactional
class DetailTravelServiceImplTest {


	@Autowired
	DetailTravelService detailTravelService;

	@Autowired
	DetailTravelRepository detailTravelRepository;


	@Autowired
	MainTravelService mainTravelService;

	@Autowired
	MainTravelRepository mainTravelRepository;


	@Autowired
	MemberRepository memberRepository;

	@MockBean
	AuthService authService;
	@Autowired
	EntityManager em;




	private void clear(){
		em.flush();
		em.clear();
	}



	private long getOtherMemberId() {
		return authService.getLoginMemberId() + 999;
	}


	@BeforeEach
	private void setUp() {
		Member member = memberRepository.save(defaultMember());
		given(authService.getLoginMemberId()).willReturn(member.getId());
		clear();
	}



	@Test
	@DisplayName("Detail Travel 조회 (성공) [public 게시물, 완성, 주인의 조회 요쳥]")
	public void successGetDetailTravelWherePublicAndCompleteAndRequestByOwner() throws Exception {
		//given
		MainTravelSaveDto mainTravelSaveDto = publicCompleteMainTravelSaveDto();
		DetailTravelSaveDto detailTravelSaveDto = mainTravelSaveDto.detailTravelSaveDtoList().get(0);
		Long mainTravelId = mainTravelService.saveMainTravel(authService.getLoginMemberId(),mainTravelSaveDto);
		List<Long> detailTravelIdSet = mappingToList(getDetailTravels(mainTravelId), DetailTravel::getId);
		Long detailTravelId = detailTravelIdSet.get(0);
		clear();


		//when
		DetailTravelDto detailTravelDto = detailTravelService.getById(authService.getLoginMemberId(), detailTravelId);

		//then
		assertThat(detailTravelDto.detailTravelId()).isEqualTo(detailTravelId);
		assertThat(detailTravelDto.title()).isEqualTo(detailTravelSaveDto.title());
		assertThat(detailTravelDto.review()).isEqualTo(detailTravelSaveDto.review());
		assertThat(detailTravelDto.tip()).isEqualTo(detailTravelSaveDto.tip());
		assertThat(detailTravelDto.visitedDate()).isEqualTo(detailTravelSaveDto.visitedDate());
		assertThat(detailTravelDto.address()).isEqualTo(detailTravelSaveDto.address());
		assertThat(detailTravelDto.createdAt()).isBefore(LocalDateTime.now().plusSeconds(2));
		int priceSize = detailTravelDto.priceDtoList().size();
		assertThat(priceSize).isEqualTo(detailTravelSaveDto.priceSaveDtoList().size());
		for (int i = 0; i < priceSize; i++) {
			PriceDto priceDto = detailTravelDto.priceDtoList().get(i);
			PriceSaveDto priceSaveDto = detailTravelSaveDto.priceSaveDtoList().get(i);
			assertThat(priceDto.priceInfo()).isEqualTo(priceSaveDto.priceInfo());
			assertThat(priceDto.item()).isEqualTo(priceSaveDto.item());
		}


		assertThat(detailTravelDto.imageDtoList().size()).isEqualTo(detailTravelSaveDto.imageSaveDtoList().size());
		int imageSize = detailTravelDto.imageDtoList().size();
		assertThat(imageSize).isEqualTo(detailTravelSaveDto.imageSaveDtoList().size());
		for (int i = 0; i < imageSize; i++) {
			ImageDto imageDto = detailTravelDto.imageDtoList().get(i);
			ImageSaveDto imageSaveDto = detailTravelSaveDto.imageSaveDtoList().get(i);
			assertThat(imageDto.path()).isEqualTo(imageSaveDto.path());
		}


	}

	private List<DetailTravel> getDetailTravels(Long mainTravelId) {
		return mainTravelRepository.findById(mainTravelId)
			.orElseThrow(() -> new MainTravelException(MainTravelExceptionType.NOT_FOUND))
			.getDetailTravels();
	}

	@Test
	@DisplayName("Main Travel 조회 (성공) [private 게시물, 완성, 주인의 조회 요쳥]")
	public void successGetDetailTravelWherePrivateAndCompleteAndRequestByOwner() throws Exception {

		//given
		MainTravelSaveDto mainTravelSaveDto = privateCompleteMainTravelSaveDto();
		DetailTravelSaveDto detailTravelSaveDto = mainTravelSaveDto.detailTravelSaveDtoList().get(0);
		Long mainTravelId = mainTravelService.saveMainTravel(authService.getLoginMemberId(),mainTravelSaveDto);
		List<Long> detailTravelIdSet = mappingToList(getDetailTravels(mainTravelId), DetailTravel::getId);
		Long detailTravelId = detailTravelIdSet.get(0);
		clear();


		//when
		DetailTravelDto detailTravelDto = detailTravelService.getById(authService.getLoginMemberId(), detailTravelId);

		//then
		assertThat(detailTravelDto.detailTravelId()).isEqualTo(detailTravelId);
		assertThat(detailTravelDto.title()).isEqualTo(detailTravelSaveDto.title());
		assertThat(detailTravelDto.review()).isEqualTo(detailTravelSaveDto.review());
		assertThat(detailTravelDto.tip()).isEqualTo(detailTravelSaveDto.tip());
		assertThat(detailTravelDto.visitedDate()).isEqualTo(detailTravelSaveDto.visitedDate());
		assertThat(detailTravelDto.address()).isEqualTo(detailTravelSaveDto.address());
		assertThat(detailTravelDto.createdAt()).isBefore(LocalDateTime.now().plusSeconds(2));
		assertThat(detailTravelDto.priceDtoList().size()).isEqualTo(detailTravelSaveDto.priceSaveDtoList().size());
		int priceSize = detailTravelDto.priceDtoList().size();
		assertThat(priceSize).isEqualTo(detailTravelSaveDto.priceSaveDtoList().size());
		for (int i = 0; i < priceSize; i++) {
			PriceDto priceDto = detailTravelDto.priceDtoList().get(i);
			PriceSaveDto priceSaveDto = detailTravelSaveDto.priceSaveDtoList().get(i);
			assertThat(priceDto.priceInfo()).isEqualTo(priceSaveDto.priceInfo());
			assertThat(priceDto.item()).isEqualTo(priceSaveDto.item());
		}


		assertThat(detailTravelDto.imageDtoList().size()).isEqualTo(detailTravelSaveDto.imageSaveDtoList().size());
		int imageSize = detailTravelDto.imageDtoList().size();
		assertThat(imageSize).isEqualTo(detailTravelSaveDto.imageSaveDtoList().size());
		for (int i = 0; i < imageSize; i++) {
			ImageDto imageDto = detailTravelDto.imageDtoList().get(i);
			ImageSaveDto imageSaveDto = detailTravelSaveDto.imageSaveDtoList().get(i);
			assertThat(imageDto.path()).isEqualTo(imageSaveDto.path());
		}


	}

	@Test
	@DisplayName("Main Travel 조회 (성공) [public 게시물, 미완성, 주인의 조회 요쳥]")
	public void successGetDetailTravelWherePublicAndUnCompleteAndRequestByOwner() throws Exception {

		//given
		MainTravelSaveDto mainTravelSaveDto = publicUnCompleteMainTravelSaveDto();
		DetailTravelSaveDto detailTravelSaveDto = mainTravelSaveDto.detailTravelSaveDtoList().get(0);
		Long mainTravelId = mainTravelService.saveMainTravel(authService.getLoginMemberId(),mainTravelSaveDto);
		List<Long> detailTravelIdSet = mappingToList(getDetailTravels(mainTravelId), DetailTravel::getId);
		Long detailTravelId = detailTravelIdSet.get(0);
		clear();


		//when
		DetailTravelDto detailTravelDto = detailTravelService.getById(authService.getLoginMemberId(), detailTravelId);

		//then
		assertThat(detailTravelDto.detailTravelId()).isEqualTo(detailTravelId);
		assertThat(detailTravelDto.title()).isEqualTo(detailTravelSaveDto.title());
		assertThat(detailTravelDto.review()).isEqualTo(detailTravelSaveDto.review());
		assertThat(detailTravelDto.tip()).isEqualTo(detailTravelSaveDto.tip());
		assertThat(detailTravelDto.visitedDate()).isEqualTo(detailTravelSaveDto.visitedDate());
		assertThat(detailTravelDto.address()).isEqualTo(detailTravelSaveDto.address());
		assertThat(detailTravelDto.createdAt()).isBefore(LocalDateTime.now().plusSeconds(2));
		assertThat(detailTravelDto.priceDtoList().size()).isEqualTo(detailTravelSaveDto.priceSaveDtoList().size());
		int priceSize = detailTravelDto.priceDtoList().size();
		assertThat(priceSize).isEqualTo(detailTravelSaveDto.priceSaveDtoList().size());
		for (int i = 0; i < priceSize; i++) {
			PriceDto priceDto = detailTravelDto.priceDtoList().get(i);
			PriceSaveDto priceSaveDto = detailTravelSaveDto.priceSaveDtoList().get(i);
			assertThat(priceDto.priceInfo()).isEqualTo(priceSaveDto.priceInfo());
			assertThat(priceDto.item()).isEqualTo(priceSaveDto.item());
		}


		assertThat(detailTravelDto.imageDtoList().size()).isEqualTo(detailTravelSaveDto.imageSaveDtoList().size());
		int imageSize = detailTravelDto.imageDtoList().size();
		assertThat(imageSize).isEqualTo(detailTravelSaveDto.imageSaveDtoList().size());
		for (int i = 0; i < imageSize; i++) {
			ImageDto imageDto = detailTravelDto.imageDtoList().get(i);
			ImageSaveDto imageSaveDto = detailTravelSaveDto.imageSaveDtoList().get(i);
			assertThat(imageDto.path()).isEqualTo(imageSaveDto.path());
		}
	}
	@Test
	@DisplayName("Main Travel 조회 (성공) [private 게시물, 미완성, 주인의 조회 요쳥]")
	public void successGetDetailTravelWherePrivateAndUnCompleteAndRequestByOwner() throws Exception {

		//given
		MainTravelSaveDto mainTravelSaveDto = privateUnCompleteMainTravelSaveDto();
		DetailTravelSaveDto detailTravelSaveDto = mainTravelSaveDto.detailTravelSaveDtoList().get(0);
		Long mainTravelId = mainTravelService.saveMainTravel(authService.getLoginMemberId(),mainTravelSaveDto);
		List<Long> detailTravelIdSet = mappingToList(getDetailTravels(mainTravelId), DetailTravel::getId);
		Long detailTravelId = detailTravelIdSet.get(0);
		clear();


		//when
		DetailTravelDto detailTravelDto = detailTravelService.getById(authService.getLoginMemberId(), detailTravelId);

		//then
		assertThat(detailTravelDto.detailTravelId()).isEqualTo(detailTravelId);
		assertThat(detailTravelDto.title()).isEqualTo(detailTravelSaveDto.title());
		assertThat(detailTravelDto.review()).isEqualTo(detailTravelSaveDto.review());
		assertThat(detailTravelDto.tip()).isEqualTo(detailTravelSaveDto.tip());
		assertThat(detailTravelDto.visitedDate()).isEqualTo(detailTravelSaveDto.visitedDate());
		assertThat(detailTravelDto.address()).isEqualTo(detailTravelSaveDto.address());
		assertThat(detailTravelDto.createdAt()).isBefore(LocalDateTime.now().plusSeconds(2));
		assertThat(detailTravelDto.priceDtoList().size()).isEqualTo(detailTravelSaveDto.priceSaveDtoList().size());
		int priceSize = detailTravelDto.priceDtoList().size();
		assertThat(priceSize).isEqualTo(detailTravelSaveDto.priceSaveDtoList().size());
		for (int i = 0; i < priceSize; i++) {
			PriceDto priceDto = detailTravelDto.priceDtoList().get(i);
			PriceSaveDto priceSaveDto = detailTravelSaveDto.priceSaveDtoList().get(i);
			assertThat(priceDto.priceInfo()).isEqualTo(priceSaveDto.priceInfo());
			assertThat(priceDto.item()).isEqualTo(priceSaveDto.item());
		}


		assertThat(detailTravelDto.imageDtoList().size()).isEqualTo(detailTravelSaveDto.imageSaveDtoList().size());
		int imageSize = detailTravelDto.imageDtoList().size();
		assertThat(imageSize).isEqualTo(detailTravelSaveDto.imageSaveDtoList().size());
		for (int i = 0; i < imageSize; i++) {
			ImageDto imageDto = detailTravelDto.imageDtoList().get(i);
			ImageSaveDto imageSaveDto = detailTravelSaveDto.imageSaveDtoList().get(i);
			assertThat(imageDto.path()).isEqualTo(imageSaveDto.path());
		}
	}

	@Test
	@DisplayName("Main Travel 조회 (성공) [public 게시물, 완성, 다른 사람의 조회 요쳥]")
	public void successGetDetailTravelWherePublicAndCompleteAndRequestByOther() throws Exception {
		MainTravelSaveDto mainTravelSaveDto = publicCompleteMainTravelSaveDto();
		DetailTravelSaveDto detailTravelSaveDto = mainTravelSaveDto.detailTravelSaveDtoList().get(0);
		Long mainTravelId = mainTravelService.saveMainTravel(authService.getLoginMemberId(),mainTravelSaveDto);
		List<Long> detailTravelIdSet = mappingToList(getDetailTravels(mainTravelId), DetailTravel::getId);
		Long detailTravelId = detailTravelIdSet.get(0);
		clear();


		//when
		DetailTravelDto detailTravelDto = detailTravelService.getById(authService.getLoginMemberId(), detailTravelId);

		//then
		assertThat(detailTravelDto.detailTravelId()).isEqualTo(detailTravelId);
		assertThat(detailTravelDto.title()).isEqualTo(detailTravelSaveDto.title());
		assertThat(detailTravelDto.review()).isEqualTo(detailTravelSaveDto.review());
		assertThat(detailTravelDto.tip()).isEqualTo(detailTravelSaveDto.tip());
		assertThat(detailTravelDto.visitedDate()).isEqualTo(detailTravelSaveDto.visitedDate());
		assertThat(detailTravelDto.address()).isEqualTo(detailTravelSaveDto.address());
		assertThat(detailTravelDto.createdAt()).isBefore(LocalDateTime.now().plusSeconds(2));
		assertThat(detailTravelDto.priceDtoList().size()).isEqualTo(detailTravelSaveDto.priceSaveDtoList().size());
		int priceSize = detailTravelDto.priceDtoList().size();
		assertThat(priceSize).isEqualTo(detailTravelSaveDto.priceSaveDtoList().size());
		for (int i = 0; i < priceSize; i++) {
			PriceDto priceDto = detailTravelDto.priceDtoList().get(i);
			PriceSaveDto priceSaveDto = detailTravelSaveDto.priceSaveDtoList().get(i);
			assertThat(priceDto.priceInfo()).isEqualTo(priceSaveDto.priceInfo());
			assertThat(priceDto.item()).isEqualTo(priceSaveDto.item());
		}


		assertThat(detailTravelDto.imageDtoList().size()).isEqualTo(detailTravelSaveDto.imageSaveDtoList().size());
		int imageSize = detailTravelDto.imageDtoList().size();
		assertThat(imageSize).isEqualTo(detailTravelSaveDto.imageSaveDtoList().size());
		for (int i = 0; i < imageSize; i++) {
			ImageDto imageDto = detailTravelDto.imageDtoList().get(i);
			ImageSaveDto imageSaveDto = detailTravelSaveDto.imageSaveDtoList().get(i);
			assertThat(imageDto.path()).isEqualTo(imageSaveDto.path());
		}
	}


	@Test
	@DisplayName("Main Travel 조회 (실패) [private 게시물, 완성, 다른 사람의 조회 요쳥]")
	public void failGetDetailTravelWherePrivateAndCompleteAndRequestByOther() throws Exception {

		//given
		MainTravelSaveDto mainTravelSaveDto = privateCompleteMainTravelSaveDto();
		DetailTravelSaveDto detailTravelSaveDto = mainTravelSaveDto.detailTravelSaveDtoList().get(0);
		Long mainTravelId = mainTravelService.saveMainTravel(authService.getLoginMemberId(),mainTravelSaveDto);
		List<Long> detailTravelIdSet = mappingToList(getDetailTravels(mainTravelId), DetailTravel::getId);
		Long detailTravelId = detailTravelIdSet.get(0);
		clear();


		//when, then
		assertThat(assertThrows(DetailTravelException.class, () -> detailTravelService.getById(getOtherMemberId(), detailTravelId)).getExceptionType())
			.isEqualTo(NO_AUTHORITY);

	}


	@Test
	@DisplayName("Main Travel 조회 (실패) [public 게시물, 미완성, 다른 사람의 조회 요쳥]")
	public void failGetDetailTravelWherePublicAndUnCompleteAndRequestByOther() throws Exception {
		//given
		MainTravelSaveDto mainTravelSaveDto = publicUnCompleteMainTravelSaveDto();
		DetailTravelSaveDto detailTravelSaveDto = mainTravelSaveDto.detailTravelSaveDtoList().get(0);
		Long mainTravelId = mainTravelService.saveMainTravel(authService.getLoginMemberId(),mainTravelSaveDto);
		List<Long> detailTravelIdSet = mappingToList(getDetailTravels(mainTravelId), DetailTravel::getId);
		Long detailTravelId = detailTravelIdSet.get(0);
		clear();


		//when, then
		assertThat(assertThrows(
			DetailTravelException.class, () -> detailTravelService.getById(getOtherMemberId(), detailTravelId)).getExceptionType())
			.isEqualTo(NO_AUTHORITY);
	}

	@Test
	@DisplayName("Main Travel 조회 (실패) [private 게시물, 미완성, 다른 사람의 조회 요쳥]")
	public void failGetDetailTravelWherePrivateAndUnCompleteAndRequestByOther() throws Exception {

		//given
		MainTravelSaveDto mainTravelSaveDto = privateUnCompleteMainTravelSaveDto();
		DetailTravelSaveDto detailTravelSaveDto = mainTravelSaveDto.detailTravelSaveDtoList().get(0);
		Long mainTravelId = mainTravelService.saveMainTravel(authService.getLoginMemberId(),mainTravelSaveDto);
		List<Long> detailTravelIdSet = mappingToList(getDetailTravels(mainTravelId), DetailTravel::getId);
		Long detailTravelId = detailTravelIdSet.get(0);
		clear();


		//when, then
		assertThat(assertThrows(DetailTravelException.class, () -> detailTravelService.getById(getOtherMemberId(), detailTravelId)).getExceptionType())
			.isEqualTo(NO_AUTHORITY);
	}


	@Test
	@DisplayName("Main Travel 조회 (실패 - 없는 게시물)")
	public void failGetMainTravelCauseNotExist() throws Exception {
		//given
		Long nullId = 9999999L;

		//when, then
		assertThat(assertThrows(DetailTravelException.class, () -> detailTravelService.getById(authService.getLoginMemberId(), nullId)).getExceptionType())
			.isEqualTo(NOT_FOUND);

	}

	
}