package com.footprint.maintravel.service;

import static com.footprint.maintravel.exception.MainTravelExceptionType.*;
import static com.footprint.maintravel.fixture.MainTravelFixture.*;
import static com.footprint.member.fixture.MemberFixture.*;
import static com.footprint.util.MappingTestUtil.*;
import static java.time.LocalDate.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;

import org.assertj.core.api.AbstractThrowableAssert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;

import com.footprint.auth.service.AuthService;
import com.footprint.detailtravel.domain.Address;
import com.footprint.detailtravel.domain.DetailTravel;
import com.footprint.detailtravel.service.dto.create.DetailTravelSaveDto;
import com.footprint.detailtravel.service.dto.info.SimpleDetailTravelDto;
import com.footprint.image.domain.Image;
import com.footprint.image.service.dto.ImageSaveDto;
import com.footprint.maintravel.domain.MainTravel;
import com.footprint.maintravel.exception.MainTravelException;
import com.footprint.maintravel.exception.MainTravelExceptionType;
import com.footprint.maintravel.repository.MainTravelRepository;
import com.footprint.maintravel.service.dto.info.MainTravelInfoDto;
import com.footprint.maintravel.service.dto.save.MainTravelSaveDto;
import com.footprint.maintravel.service.dto.update.MainTravelUpdateDto;
import com.footprint.member.domain.Member;
import com.footprint.member.repository.MemberRepository;
import com.footprint.price.domain.Price;
import com.footprint.price.service.dto.PriceSaveDto;

/**
 * Created by ShinD on 2022/06/26.
 */
@Transactional
@SpringBootTest
class MainTravelServiceTest {

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
	@DisplayName("MainTravel과 DetailTravel 저장 (성공)")
	public void successSaveTravel() throws Exception {
	    //given
		MainTravelSaveDto mainTravelSaveDto = mainTravelSaveDto();

	    //when
		Long mainTravelId = mainTravelService.saveMainTravel(authService.getLoginMemberId(), mainTravelSaveDto);
		clear();

	    //then
		MainTravel mainTravel = mainTravelRepository.findById(mainTravelId)
		 	.orElseThrow(() -> new MainTravelException(NOT_FOUND));

		assertThat(mainTravel.getTitle()).isEqualTo(mainTravelSaveDto.title());
		assertThat(mainTravel.getWriter().getId()).isEqualTo(authService.getLoginMemberId());
		assertThat(mainTravel.getImagePath()).isEqualTo(mainTravelSaveDto.mainImagePath());
		assertThat(mainTravel.getDetailTravels().size()).isEqualTo(mainTravelSaveDto.detailTravelSaveDtoList().size());
		assertThat(mainTravel.getDetailTravels().size()).isNotEqualTo(0);

		int size = mainTravelSaveDto.detailTravelSaveDtoList().size();
		for (int i = 0; i < size; i++) {
			DetailTravel detailTravel = mainTravel.getDetailTravels().get(i);
			DetailTravelSaveDto detailTravelSaveDto = mainTravelSaveDto.detailTravelSaveDtoList().get(i);
			int priceSize = detailTravel.getPrices().size();
			assertThat(priceSize).isNotEqualTo(0);
			for (int j = 0; j < priceSize; j++) {
				Price price = detailTravel.getPrices().get(j);
				PriceSaveDto priceSaveDto = detailTravelSaveDto.priceSaveDtoList().get(j);
				assertThat(price.getItem()).isEqualTo(priceSaveDto.item());
				assertThat(price.getPriceInfo()).isEqualTo(priceSaveDto.priceInfo());
				assertThat(price.getId()).isNotNull();
			}

			int imageSize = detailTravel.getImages().size();
			assertThat(imageSize).isNotEqualTo(0);
			for (int j = 0; j < imageSize; j++) {
				Image image = detailTravel.getImages().get(j);
				ImageSaveDto imageSaveDto = detailTravelSaveDto.imageSaveDtoList().get(j);
				assertThat(image.getPath()).isEqualTo(imageSaveDto.path());
				assertThat(image.getId()).isNotNull();
			}
		}
		Set<String> dtoSet = mappingToSet(mainTravelSaveDto.detailTravelSaveDtoList(), DetailTravelSaveDto::title);
		Set<String> entitySet = mappingToSet(mainTravel.getDetailTravels(), DetailTravel::getTitle);

		assertThat(entitySet).containsAll(dtoSet);
	}



	@Test
	@DisplayName("MainTravel과 DetailTravel 수정 (성공)")
	public void successUpdateTravel() throws Exception {
		//given
		MainTravelSaveDto mainTravelSaveDto = mainTravelSaveDto();
		Long mainTravelId = mainTravelService.saveMainTravel(authService.getLoginMemberId(), mainTravelSaveDto);
		clear();

		MainTravelUpdateDto mainTravelUpdateDto = mainTravelUpdateDto(mainTravelId);

		//when
		mainTravelService.updateMainTravel(authService.getLoginMemberId(), mainTravelUpdateDto);
		clear();



		//then
		MainTravel mainTravel = mainTravelRepository.findById(mainTravelId)
			.orElseThrow(() -> new MainTravelException(NOT_FOUND));

		assertThat(mainTravel.getTitle()).isEqualTo(mainTravelUpdateDto.title());
		assertThat(mainTravel.getTitle()).isNotEqualTo(mainTravelSaveDto.title());
		assertThat(mainTravel.getImagePath()).isEqualTo(mainTravelSaveDto.mainImagePath());
		assertThat(mainTravel.getDetailTravels().size()).isEqualTo(mainTravelUpdateDto.detailTravelSaveDtoList().size());
		assertThat(mainTravel.getDetailTravels().size()).isNotEqualTo(mainTravelSaveDto.detailTravelSaveDtoList().size());

		assertThat(mainTravel.getWriter().getId()).isEqualTo(authService.getLoginMemberId());


		Set<String> saveDtoSet = mappingToSet(mainTravelSaveDto.detailTravelSaveDtoList(), DetailTravelSaveDto::title);
		Set<String> updateDtoSet = mappingToSet(mainTravelUpdateDto.detailTravelSaveDtoList(), DetailTravelSaveDto::title);
		Set<String> entitySet = mappingToSet(mainTravel.getDetailTravels(), DetailTravel::getTitle);

		assertThat(entitySet).containsAll(updateDtoSet);
		assertThat(entitySet).doesNotContainAnyElementsOf(saveDtoSet);



		int size = mainTravelUpdateDto.detailTravelSaveDtoList().size();
		for (int i = 0; i < size; i++) {
			DetailTravel detailTravel = mainTravel.getDetailTravels().get(i);
			DetailTravelSaveDto detailTravelSaveDto = mainTravelUpdateDto.detailTravelSaveDtoList().get(i);
			int priceSize = detailTravel.getPrices().size();
			assertThat(priceSize).isNotEqualTo(0);
			for (int j = 0; j < priceSize; j++) {
				Price price = detailTravel.getPrices().get(j);
				PriceSaveDto priceSaveDto = detailTravelSaveDto.priceSaveDtoList().get(j);
				assertThat(price.getItem()).isEqualTo(priceSaveDto.item());
				assertThat(price.getPriceInfo()).isEqualTo(priceSaveDto.priceInfo());
				assertThat(price.getId()).isNotNull();
			}

			int imageSize = detailTravel.getImages().size();
			assertThat(imageSize).isNotEqualTo(0);
			for (int j = 0; j < imageSize; j++) {
				Image image = detailTravel.getImages().get(j);
				ImageSaveDto imageSaveDto = detailTravelSaveDto.imageSaveDtoList().get(j);
				assertThat(image.getPath()).isEqualTo(imageSaveDto.path());
				assertThat(image.getId()).isNotNull();
			}
		}
	}


	@Test
	@DisplayName("MainTravel과 DetailTravel 수정 (실패 - 원인 : 권한없음)")
	public void failUpdateTravelCauseNoAuthority() throws Exception {
		//given
		MainTravelSaveDto mainTravelSaveDto = mainTravelSaveDto();
		Long mainTravelId = mainTravelService.saveMainTravel(authService.getLoginMemberId(), mainTravelSaveDto);
		clear();

		MainTravelUpdateDto mainTravelUpdateDto = mainTravelUpdateDto(mainTravelId);

		//when
		AbstractThrowableAssert<?, ?> cause = assertThatThrownBy(
			() -> mainTravelService.updateMainTravel(getOtherMemberId(), mainTravelUpdateDto))
			.isInstanceOf(MainTravelException.class);

		clear();



		//then
		MainTravel mainTravel = mainTravelRepository.findById(mainTravelId)
			.orElseThrow(() -> new MainTravelException(NOT_FOUND));

		assertThat(mainTravel.getTitle()).isNotEqualTo(mainTravelUpdateDto.title());
		assertThat(mainTravel.getTitle()).isEqualTo(mainTravelSaveDto.title());
		assertThat(mainTravel.getImagePath()).isNotEqualTo(mainTravelUpdateDto.mainImagePath());
		assertThat(mainTravel.getImagePath()).isEqualTo(mainTravelSaveDto.mainImagePath());
		assertThat(mainTravel.getDetailTravels().size()).isNotEqualTo(mainTravelUpdateDto.detailTravelSaveDtoList().size());
		assertThat(mainTravel.getDetailTravels().size()).isEqualTo(mainTravelSaveDto.detailTravelSaveDtoList().size());

		assertThat(mainTravel.getWriter().getId()).isEqualTo(authService.getLoginMemberId());

		Set<String> saveDtoSet = mappingToSet(mainTravelSaveDto.detailTravelSaveDtoList(), DetailTravelSaveDto::title);
		Set<String> updateDtoSet = mappingToSet(mainTravelUpdateDto.detailTravelSaveDtoList(), DetailTravelSaveDto::title);
		Set<String> entitySet = mappingToSet(mainTravel.getDetailTravels(), DetailTravel::getTitle);

		assertThat(entitySet).doesNotContainAnyElementsOf(updateDtoSet);
		assertThat(entitySet).containsAll(saveDtoSet);


		int size = mainTravelSaveDto.detailTravelSaveDtoList().size();
		for (int i = 0; i < size; i++) {
			DetailTravel detailTravel = mainTravel.getDetailTravels().get(i);
			DetailTravelSaveDto detailTravelSaveDto = mainTravelSaveDto.detailTravelSaveDtoList().get(i);
			int priceSize = detailTravel.getPrices().size();
			assertThat(priceSize).isNotEqualTo(0);
			for (int j = 0; j < priceSize; j++) {
				Price price = detailTravel.getPrices().get(j);
				PriceSaveDto priceSaveDto = detailTravelSaveDto.priceSaveDtoList().get(j);
				assertThat(price.getItem()).isEqualTo(priceSaveDto.item());
				assertThat(price.getPriceInfo()).isEqualTo(priceSaveDto.priceInfo());
				assertThat(price.getId()).isNotNull();
			}

			int imageSize = detailTravel.getImages().size();
			assertThat(imageSize).isNotEqualTo(0);
			for (int j = 0; j < imageSize; j++) {
				Image image = detailTravel.getImages().get(j);
				ImageSaveDto imageSaveDto = detailTravelSaveDto.imageSaveDtoList().get(j);
				assertThat(image.getPath()).isEqualTo(imageSaveDto.path());
				assertThat(image.getId()).isNotNull();
			}
		}

	}



	@Test
	@DisplayName("MainTravel과 DetailTravel 삭제 (성공)")
	public void successDeleteTravel() throws Exception {
		//given
		MainTravelSaveDto mainTravelSaveDto = mainTravelSaveDto();
		Long mainTravelId = mainTravelService.saveMainTravel(authService.getLoginMemberId(), mainTravelSaveDto);
		List<Long> detailTravelIdList = getDetailTravelByMainTravelId(mainTravelId).stream().map(DetailTravel::getId).toList();
		clear();


		//when
		mainTravelService.deleteMainTravel(authService.getLoginMemberId(), mainTravelId);
		clear();



		//then
		assertThat(detailTravelIdList.size()).isNotEqualTo(0);
		for (int i = 0; i < detailTravelIdList.size(); i++) {
			Long detailTravelId = detailTravelIdList.get(i);
			List<Price> priceList = getPriceListByDetailTravelId(detailTravelId);
			List<Image> imageList = getImageListByDetailTravelId(detailTravelId);
			assertThat(priceList).isEmpty();
			assertThat(imageList).isEmpty();
		}

		List<DetailTravel> detailTravelList = getDetailTravelByMainTravelId(mainTravelId);
		assertThat(detailTravelList).isEmpty();


		assertThatThrownBy(() -> mainTravelRepository.findById(mainTravelId).orElseThrow(() -> new MainTravelException(NOT_FOUND)))
			.isInstanceOf(MainTravelException.class);

	}

	private List<Image> getImageListByDetailTravelId(Long detailTravelId) {
		return em.createQuery(
				"select i from Image i where i.detailTravel.id = :id",
				Image.class)
			.setParameter("id", detailTravelId)
			.getResultList();
	}

	private List<DetailTravel> getDetailTravelByMainTravelId(Long mainTravelId) {
		return em.createQuery(
				"select dt from DetailTravel dt where dt.mainTravel.id = :id",
				DetailTravel.class)
			.setParameter("id", mainTravelId)
			.getResultList();
	}

	private List<Price> getPriceListByDetailTravelId(Long detailTravelId) {
		return em.createQuery(
				"select p from Price p where p.detailTravel.id = :id",
				Price.class)
			.setParameter("id", detailTravelId)
			.getResultList();
	}

	@Test
	@DisplayName("MainTravel과 DetailTravel 삭제 (실패 : 원인 - 권한없음)")
	public void failDeleteTravel() throws Exception {
		//given
		MainTravelSaveDto mainTravelSaveDto = mainTravelSaveDto();
		Long mainTravelId = mainTravelService.saveMainTravel(authService.getLoginMemberId(), mainTravelSaveDto);
		List<Long> detailTravelIdList =
			getDetailTravelByMainTravelId(mainTravelId).stream().map(DetailTravel::getId).toList();
		clear();


		//when
		AbstractThrowableAssert<?, ?> cause = assertThatThrownBy(
			() -> mainTravelService.deleteMainTravel(getOtherMemberId(), mainTravelId))
			.isInstanceOf(MainTravelException.class);

		clear();



		//then
		assertThat(detailTravelIdList.size()).isNotEqualTo(0);
		for (int i = 0; i < detailTravelIdList.size(); i++) {
			Long detailTravelId = detailTravelIdList.get(i);
			List<Price> priceList = getPriceListByDetailTravelId(detailTravelId);
			List<Image> imageList = getImageListByDetailTravelId(detailTravelId);
			assertThat(priceList).isNotEmpty();
			assertThat(imageList).isNotEmpty();
		}
		MainTravel mainTravel = mainTravelRepository.findById(mainTravelId)
			.orElseThrow(() -> new MainTravelException(NOT_FOUND));

		assertThat(mainTravel.getTitle()).isEqualTo(mainTravelSaveDto.title());
		assertThat(mainTravel.getWriter().getId()).isEqualTo(authService.getLoginMemberId());
		assertThat(mainTravel.getDetailTravels().size()).isEqualTo(mainTravelSaveDto.detailTravelSaveDtoList().size());




		Set<String> dtoSet = mappingToSet(mainTravelSaveDto.detailTravelSaveDtoList(), DetailTravelSaveDto::title);
		Set<String> entitySet = mappingToSet(mainTravel.getDetailTravels(), DetailTravel::getTitle);

		assertThat(entitySet).containsAll(dtoSet);
	}








	@Test
	@DisplayName("Main Travel 조회 (성공) [public 게시물, 완성, 주인의 조회 요쳥]")
	public void successGetMainTravelWherePublicAndCompleteAndRequestByOwner() throws Exception {
	    //given
		MainTravelSaveDto mainTravelSaveDto = publicCompleteMainTravelSaveDto();
		Long mainTravelId = mainTravelService.saveMainTravel(authService.getLoginMemberId(),mainTravelSaveDto);
		clear();


	    //when
		MainTravelInfoDto mainTravelInfo = mainTravelService.getMainTravel(authService.getLoginMemberId(), mainTravelId);


		//then
		assertThat(mainTravelInfo.id()).isEqualTo(mainTravelId);

		assertThat(mainTravelInfo.writerInfo().id()).isEqualTo(authService.getLoginMemberId());
		assertThat(mainTravelInfo.writerInfo().username()).isEqualTo(USERNAME);
		assertThat(mainTravelInfo.writerInfo().nickname()).isEqualTo(NICKNAME);

		assertThat(mainTravelInfo.title()).isEqualTo(mainTravelSaveDto.title());
		assertThat(parse(mainTravelInfo.startDate())).isEqualTo(mainTravelSaveDto.startDate());
		assertThat(parse(mainTravelInfo.endDate())).isEqualTo(mainTravelSaveDto.endDate());


		assertThat(LocalDateTime.parse(mainTravelInfo.createdAt())).isBefore(LocalDateTime.now().plusSeconds(2));
		//Auditing 에서 들어갈때 소수점을 반올림해서 들어감 -> now는 반올림이 안되므로 더 작아져버림

		assertThat(mainTravelInfo.isVisible()).isEqualTo(mainTravelSaveDto.isVisible());
		assertThat(mainTravelInfo.isCompleted()).isEqualTo(mainTravelSaveDto.isCompleted());

		assertThat(mainTravelInfo.mainImagePath()).isEqualTo(mainTravelSaveDto.mainImagePath());
		assertThat(mainTravelInfo.likeNum()).isEqualTo(0);

		assertThat(mainTravelInfo.simpleDetailTravelListDto().total()).isEqualTo(mainTravelSaveDto.detailTravelSaveDtoList().size());



		Set<String> dtoSet = mappingToSet(mainTravelSaveDto.detailTravelSaveDtoList(), DetailTravelSaveDto::title);

		Set<String> entitySet = mappingToSet(mainTravelInfo.simpleDetailTravelListDto().detailTravelDtoList(),SimpleDetailTravelDto::title);

		Set<Address> dtoAddressSet = mappingToSet(mainTravelSaveDto.detailTravelSaveDtoList(),DetailTravelSaveDto::address);

		Set<Address> entityAddressSet = mappingToSet(mainTravelInfo.simpleDetailTravelListDto().detailTravelDtoList(),SimpleDetailTravelDto::address);


		assertThat(entityAddressSet).containsAll(dtoAddressSet);
		assertThat(entitySet).containsAll(dtoSet);


		Set<Long> mainTravelIdSet = mappingToSet(mainTravelInfo.simpleDetailTravelListDto().detailTravelDtoList(),SimpleDetailTravelDto::mainTravelId);
		assertThat(mainTravelIdSet.size()).isEqualTo(1);
		assertThat(mainTravelIdSet).containsOnly(mainTravelId);
	}




	@Test
	@DisplayName("Main Travel 조회 (성공) [private 게시물, 완성, 주인의 조회 요쳥]")
	public void successGetMainTravelWherePrivateAndCompleteAndRequestByOwner() throws Exception {

		//given
		MainTravelSaveDto mainTravelSaveDto = privateCompleteMainTravelSaveDto();
		Long mainTravelId = mainTravelService.saveMainTravel(authService.getLoginMemberId(),mainTravelSaveDto);
		clear();


		//when
		MainTravelInfoDto mainTravelInfo = mainTravelService.getMainTravel(authService.getLoginMemberId(), mainTravelId);


		//then
		assertThat(mainTravelInfo.id()).isEqualTo(mainTravelId);

		assertThat(mainTravelInfo.writerInfo().id()).isEqualTo(authService.getLoginMemberId());
		assertThat(mainTravelInfo.writerInfo().username()).isEqualTo(USERNAME);
		assertThat(mainTravelInfo.writerInfo().nickname()).isEqualTo(NICKNAME);

		assertThat(mainTravelInfo.title()).isEqualTo(mainTravelSaveDto.title());
		assertThat(parse(mainTravelInfo.startDate())).isEqualTo(mainTravelSaveDto.startDate());
		assertThat(parse(mainTravelInfo.endDate())).isEqualTo(mainTravelSaveDto.endDate());


		assertThat(LocalDateTime.parse(mainTravelInfo.createdAt())).isBefore(LocalDateTime.now().plusSeconds(2));
		//Auditing 에서 들어갈때 소수점을 반올림해서 들어감 -> now는 반올림이 안되므로 더 작아져버림

		assertThat(mainTravelInfo.isVisible()).isEqualTo(mainTravelSaveDto.isVisible());
		assertThat(mainTravelInfo.isCompleted()).isEqualTo(mainTravelSaveDto.isCompleted());

		assertThat(mainTravelInfo.mainImagePath()).isEqualTo(mainTravelSaveDto.mainImagePath());
		assertThat(mainTravelInfo.likeNum()).isEqualTo(0);

		assertThat(mainTravelInfo.simpleDetailTravelListDto().total()).isEqualTo(mainTravelSaveDto.detailTravelSaveDtoList().size());



		Set<String> dtoSet = mappingToSet(mainTravelSaveDto.detailTravelSaveDtoList(), DetailTravelSaveDto::title);

		Set<String> entitySet = mappingToSet(mainTravelInfo.simpleDetailTravelListDto().detailTravelDtoList(),SimpleDetailTravelDto::title);

		Set<Address> dtoAddressSet = mappingToSet(mainTravelSaveDto.detailTravelSaveDtoList(),DetailTravelSaveDto::address);

		Set<Address> entityAddressSet = mappingToSet(mainTravelInfo.simpleDetailTravelListDto().detailTravelDtoList(),SimpleDetailTravelDto::address);

		assertThat(entityAddressSet).containsAll(dtoAddressSet);
		assertThat(entitySet).containsAll(dtoSet);

		Set<Long> mainTravelIdSet = mappingToSet(mainTravelInfo.simpleDetailTravelListDto().detailTravelDtoList(),SimpleDetailTravelDto::mainTravelId);
		assertThat(mainTravelIdSet.size()).isEqualTo(1);
		assertThat(mainTravelIdSet).containsOnly(mainTravelId);
	}

	@Test
	@DisplayName("Main Travel 조회 (성공) [public 게시물, 미완성, 주인의 조회 요쳥]")
	public void successGetMainTravelWherePublicAndUnCompleteAndRequestByOwner() throws Exception {

		//given
		MainTravelSaveDto mainTravelSaveDto = publicUnCompleteMainTravelSaveDto();
		Long mainTravelId = mainTravelService.saveMainTravel(authService.getLoginMemberId(),mainTravelSaveDto);
		clear();


		//when
		MainTravelInfoDto mainTravelInfo = mainTravelService.getMainTravel(authService.getLoginMemberId(), mainTravelId);


		//then
		assertThat(mainTravelInfo.id()).isEqualTo(mainTravelId);

		assertThat(mainTravelInfo.writerInfo().id()).isEqualTo(authService.getLoginMemberId());
		assertThat(mainTravelInfo.writerInfo().username()).isEqualTo(USERNAME);
		assertThat(mainTravelInfo.writerInfo().nickname()).isEqualTo(NICKNAME);

		assertThat(mainTravelInfo.title()).isEqualTo(mainTravelSaveDto.title());
		assertThat(parse(mainTravelInfo.startDate())).isEqualTo(mainTravelSaveDto.startDate());
		assertThat(parse(mainTravelInfo.endDate())).isEqualTo(mainTravelSaveDto.endDate());


		assertThat(LocalDateTime.parse(mainTravelInfo.createdAt())).isBefore(LocalDateTime.now().plusSeconds(2));
		//Auditing 에서 들어갈때 소수점을 반올림해서 들어감 -> now는 반올림이 안되므로 더 작아져버림

		assertThat(mainTravelInfo.isVisible()).isEqualTo(mainTravelSaveDto.isVisible());
		assertThat(mainTravelInfo.isCompleted()).isEqualTo(mainTravelSaveDto.isCompleted());

		assertThat(mainTravelInfo.mainImagePath()).isEqualTo(mainTravelSaveDto.mainImagePath());
		assertThat(mainTravelInfo.likeNum()).isEqualTo(0);

		assertThat(mainTravelInfo.simpleDetailTravelListDto().total()).isEqualTo(mainTravelSaveDto.detailTravelSaveDtoList().size());



		Set<String> dtoSet = mappingToSet(mainTravelSaveDto.detailTravelSaveDtoList(), DetailTravelSaveDto::title);

		Set<String> entitySet = mappingToSet(mainTravelInfo.simpleDetailTravelListDto().detailTravelDtoList(),SimpleDetailTravelDto::title);

		Set<Address> dtoAddressSet = mappingToSet(mainTravelSaveDto.detailTravelSaveDtoList(),DetailTravelSaveDto::address);

		Set<Address> entityAddressSet = mappingToSet(mainTravelInfo.simpleDetailTravelListDto().detailTravelDtoList(),SimpleDetailTravelDto::address);

		assertThat(entityAddressSet).containsAll(dtoAddressSet);
		assertThat(entitySet).containsAll(dtoSet);

		Set<Long> mainTravelIdSet = mappingToSet(mainTravelInfo.simpleDetailTravelListDto().detailTravelDtoList(),SimpleDetailTravelDto::mainTravelId);
		assertThat(mainTravelIdSet.size()).isEqualTo(1);
		assertThat(mainTravelIdSet).containsOnly(mainTravelId);
	}


	@Test
	@DisplayName("Main Travel 조회 (성공) [private 게시물, 미완성, 주인의 조회 요쳥]")
	public void successGetMainTravelWherePrivateAndUnCompleteAndRequestByOwner() throws Exception {

		//given
		MainTravelSaveDto mainTravelSaveDto = privateUnCompleteMainTravelSaveDto();
		Long mainTravelId = mainTravelService.saveMainTravel(authService.getLoginMemberId(),mainTravelSaveDto);
		clear();


		//when
		MainTravelInfoDto mainTravelInfo = mainTravelService.getMainTravel(authService.getLoginMemberId(), mainTravelId);


		//then
		assertThat(mainTravelInfo.id()).isEqualTo(mainTravelId);

		assertThat(mainTravelInfo.writerInfo().id()).isEqualTo(authService.getLoginMemberId());
		assertThat(mainTravelInfo.writerInfo().username()).isEqualTo(USERNAME);
		assertThat(mainTravelInfo.writerInfo().nickname()).isEqualTo(NICKNAME);

		assertThat(mainTravelInfo.title()).isEqualTo(mainTravelSaveDto.title());
		assertThat(parse(mainTravelInfo.startDate())).isEqualTo(mainTravelSaveDto.startDate());
		assertThat(parse(mainTravelInfo.endDate())).isEqualTo(mainTravelSaveDto.endDate());


		assertThat(LocalDateTime.parse(mainTravelInfo.createdAt())).isBefore(LocalDateTime.now().plusSeconds(2));
		//Auditing 에서 들어갈때 소수점을 반올림해서 들어감 -> now는 반올림이 안되므로 더 작아져버림

		assertThat(mainTravelInfo.isVisible()).isEqualTo(mainTravelSaveDto.isVisible());
		assertThat(mainTravelInfo.isCompleted()).isEqualTo(mainTravelSaveDto.isCompleted());

		assertThat(mainTravelInfo.mainImagePath()).isEqualTo(mainTravelSaveDto.mainImagePath());
		assertThat(mainTravelInfo.likeNum()).isEqualTo(0);

		assertThat(mainTravelInfo.simpleDetailTravelListDto().total()).isEqualTo(mainTravelSaveDto.detailTravelSaveDtoList().size());



		Set<String> dtoSet = mappingToSet(mainTravelSaveDto.detailTravelSaveDtoList(), DetailTravelSaveDto::title);

		Set<String> entitySet = mappingToSet(mainTravelInfo.simpleDetailTravelListDto().detailTravelDtoList(),SimpleDetailTravelDto::title);

		Set<Address> dtoAddressSet = mappingToSet(mainTravelSaveDto.detailTravelSaveDtoList(),DetailTravelSaveDto::address);

		Set<Address> entityAddressSet = mappingToSet(mainTravelInfo.simpleDetailTravelListDto().detailTravelDtoList(),SimpleDetailTravelDto::address);

		assertThat(entityAddressSet).containsAll(dtoAddressSet);
		assertThat(entitySet).containsAll(dtoSet);

		Set<Long> mainTravelIdSet = mappingToSet(mainTravelInfo.simpleDetailTravelListDto().detailTravelDtoList(),SimpleDetailTravelDto::mainTravelId);
		assertThat(mainTravelIdSet.size()).isEqualTo(1);
		assertThat(mainTravelIdSet).containsOnly(mainTravelId);
	}



	@Test
	@DisplayName("Main Travel 조회 (성공) [public 게시물, 완성, 다른 사람의 조회 요쳥]")
	public void successGetMainTravelWherePublicAndCompleteAndRequestByOther() throws Exception {
		MainTravelSaveDto mainTravelSaveDto = publicCompleteMainTravelSaveDto();
		Long mainTravelId = mainTravelService.saveMainTravel(authService.getLoginMemberId(),mainTravelSaveDto);
		clear();


		//when
		MainTravelInfoDto mainTravelInfo = mainTravelService.getMainTravel(getOtherMemberId(), mainTravelId);


		//then
		assertThat(mainTravelInfo.id()).isEqualTo(mainTravelId);

		assertThat(mainTravelInfo.writerInfo().id()).isEqualTo(authService.getLoginMemberId());
		assertThat(mainTravelInfo.writerInfo().username()).isEqualTo(USERNAME);
		assertThat(mainTravelInfo.writerInfo().nickname()).isEqualTo(NICKNAME);

		assertThat(mainTravelInfo.title()).isEqualTo(mainTravelSaveDto.title());
		assertThat(parse(mainTravelInfo.startDate())).isEqualTo(mainTravelSaveDto.startDate());
		assertThat(parse(mainTravelInfo.endDate())).isEqualTo(mainTravelSaveDto.endDate());


		assertThat(LocalDateTime.parse(mainTravelInfo.createdAt())).isBefore(LocalDateTime.now().plusSeconds(2));
		//Auditing 에서 들어갈때 소수점을 반올림해서 들어감 -> now는 반올림이 안되므로 더 작아져버림

		assertThat(mainTravelInfo.isVisible()).isEqualTo(mainTravelSaveDto.isVisible());
		assertThat(mainTravelInfo.isCompleted()).isEqualTo(mainTravelSaveDto.isCompleted());

		assertThat(mainTravelInfo.mainImagePath()).isEqualTo(mainTravelSaveDto.mainImagePath());
		assertThat(mainTravelInfo.likeNum()).isEqualTo(0);

		assertThat(mainTravelInfo.simpleDetailTravelListDto().total()).isEqualTo(mainTravelSaveDto.detailTravelSaveDtoList().size());



		Set<String> dtoSet = mappingToSet(mainTravelSaveDto.detailTravelSaveDtoList(), DetailTravelSaveDto::title);

		Set<String> entitySet = mappingToSet(mainTravelInfo.simpleDetailTravelListDto().detailTravelDtoList(),SimpleDetailTravelDto::title);

		Set<Address> dtoAddressSet = mappingToSet(mainTravelSaveDto.detailTravelSaveDtoList(),DetailTravelSaveDto::address);

		Set<Address> entityAddressSet = mappingToSet(mainTravelInfo.simpleDetailTravelListDto().detailTravelDtoList(),SimpleDetailTravelDto::address);

		assertThat(entityAddressSet).containsAll(dtoAddressSet);
		assertThat(entitySet).containsAll(dtoSet);

		Set<Long> mainTravelIdSet = mappingToSet(mainTravelInfo.simpleDetailTravelListDto().detailTravelDtoList(),SimpleDetailTravelDto::mainTravelId);
		assertThat(mainTravelIdSet.size()).isEqualTo(1);
		assertThat(mainTravelIdSet).containsOnly(mainTravelId);
	}


	@Test
	@DisplayName("Main Travel 조회 (실패) [private 게시물, 완성, 다른 사람의 조회 요쳥]")
	public void failGetMainTravelWherePrivateAndCompleteAndRequestByOther() throws Exception {

		//given
		MainTravelSaveDto mainTravelSaveDto = privateCompleteMainTravelSaveDto();
		Long mainTravelId = mainTravelService.saveMainTravel(authService.getLoginMemberId(),mainTravelSaveDto);
		clear();


		//when, then
		assertThat(assertThrows(MainTravelException.class, () -> mainTravelService.getMainTravel(getOtherMemberId(), mainTravelId)).getExceptionType())
			.isEqualTo(NO_AUTHORITY);

	}


	@Test
	@DisplayName("Main Travel 조회 (실패) [public 게시물, 미완성, 다른 사람의 조회 요쳥]")
	public void failGetMainTravelWherePublicAndUnCompleteAndRequestByOther() throws Exception {
		//given
		MainTravelSaveDto mainTravelSaveDto = publicUnCompleteMainTravelSaveDto();
		Long mainTravelId = mainTravelService.saveMainTravel(authService.getLoginMemberId(),mainTravelSaveDto);
		clear();


		//when, then
		assertThat(assertThrows(MainTravelException.class, () -> mainTravelService.getMainTravel(getOtherMemberId(), mainTravelId)).getExceptionType())
			.isEqualTo(NO_AUTHORITY);
	}

	@Test
	@DisplayName("Main Travel 조회 (실패) [private 게시물, 미완성, 다른 사람의 조회 요쳥]")
	public void failGetMainTravelWherePrivateAndUnCompleteAndRequestByOther() throws Exception {

		//given
		MainTravelSaveDto mainTravelSaveDto = privateUnCompleteMainTravelSaveDto();
		Long mainTravelId = mainTravelService.saveMainTravel(authService.getLoginMemberId(),mainTravelSaveDto);
		clear();


		//when, then
		assertThat(assertThrows(MainTravelException.class, () -> mainTravelService.getMainTravel(getOtherMemberId(), mainTravelId)).getExceptionType())
			.isEqualTo(NO_AUTHORITY);
	}


	@Test
	@DisplayName("Main Travel 조회 (실패 - 없는 게시물)")
	public void failGetMainTravelCauseNotExist() throws Exception {
		//given
		Long nullId = 9999999L;

		//when, then
		assertThat(assertThrows(MainTravelException.class, () -> mainTravelService.getMainTravel(authService.getLoginMemberId(), nullId)).getExceptionType())
			.isEqualTo(MainTravelExceptionType.NOT_FOUND);

	}



}