package com.footprint.detailtravel.service;

import static com.footprint.auth.service.SecurityUtils.*;
import static com.footprint.detailtravel.exception.DetailTravelExceptionType.*;
import static com.footprint.maintravel.fixture.MainTravelFixture.*;
import static com.footprint.member.fixture.MemberFixture.*;
import static com.footprint.util.MappingTestUtil.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;

import com.footprint.detailtravel.domain.DetailTravel;
import com.footprint.detailtravel.exception.DetailTravelException;
import com.footprint.detailtravel.repository.DetailTravelRepository;
import com.footprint.detailtravel.service.dto.create.DetailTravelSaveDto;
import com.footprint.detailtravel.service.dto.info.DetailTravelDto;
import com.footprint.maintravel.service.MainTravelService;
import com.footprint.maintravel.service.dto.save.MainTravelSaveDto;
import com.footprint.member.domain.Member;
import com.footprint.member.repository.MemberRepository;

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
	MemberRepository memberRepository;

	@Autowired
	EntityManager em;




	private void clear(){
		em.flush();
		em.clear();
	}


	private void saveSecurityContextHolder(Member member) {
		SecurityContext context = SecurityContextHolder.createEmptyContext();
		UserDetails userDetails = User.builder()
			.username(member.getId().toString())
			.password(member.getPassword())
			.authorities(new ArrayList<>()).build();

		context.setAuthentication(new UsernamePasswordAuthenticationToken(userDetails, null));
		SecurityContextHolder.setContext(context);
	}


	private long getOtherMemberId() {
		return getLoginMemberId() + 999;
	}



	@BeforeEach
	private void setUp() {
		Member member = memberRepository.save(defaultMember());
		saveSecurityContextHolder(member);
		clear();
	}


	@Test
	@DisplayName("Detail Travel 조회 (성공) [public 게시물, 완성, 주인의 조회 요쳥]")
	public void successGetDetailTravelWherePublicAndCompleteAndRequestByOwner() throws Exception {
		//given
		MainTravelSaveDto mainTravelSaveDto = publicCompleteMainTravelSaveDto();
		DetailTravelSaveDto detailTravelSaveDto = mainTravelSaveDto.detailTravelSaveDtoList().get(0);
		Long mainTravelId = mainTravelService.saveMainTravel(getLoginMemberId(),mainTravelSaveDto);
		List<Long> detailTravelIdSet = mappingToList(detailTravelRepository.findAllByMainTravelId(mainTravelId), DetailTravel::getId);
		Long detailTravelId = detailTravelIdSet.get(0);
		clear();


		//when
		DetailTravelDto detailTravelDto = detailTravelService.getById(getLoginMemberId(), detailTravelId);

		//then
		assertThat(detailTravelDto.detailTravelId()).isEqualTo(detailTravelId);
		assertThat(detailTravelDto.title()).isEqualTo(detailTravelSaveDto.title());
		assertThat(detailTravelDto.review()).isEqualTo(detailTravelSaveDto.review());
		assertThat(detailTravelDto.tip()).isEqualTo(detailTravelSaveDto.tip());
		assertThat(detailTravelDto.visitedDate()).isEqualTo(detailTravelSaveDto.visitedDate());
		assertThat(detailTravelDto.address()).isEqualTo(detailTravelSaveDto.address());
		assertThat(detailTravelDto.createdAt()).isBefore(LocalDateTime.now().plusSeconds(2));
		assertThat(detailTravelDto.priceDtoList().size()).isEqualTo(detailTravelSaveDto.priceSaveDtoList().size());
		assertThat(detailTravelDto.imageDtoList().size()).isEqualTo(detailTravelSaveDto.imagePathList().size());


	}



	@Test
	@DisplayName("Main Travel 조회 (성공) [private 게시물, 완성, 주인의 조회 요쳥]")
	public void successGetDetailTravelWherePrivateAndCompleteAndRequestByOwner() throws Exception {

		//given
		MainTravelSaveDto mainTravelSaveDto = privateCompleteMainTravelSaveDto();
		DetailTravelSaveDto detailTravelSaveDto = mainTravelSaveDto.detailTravelSaveDtoList().get(0);
		Long mainTravelId = mainTravelService.saveMainTravel(getLoginMemberId(),mainTravelSaveDto);
		List<Long> detailTravelIdSet = mappingToList(detailTravelRepository.findAllByMainTravelId(mainTravelId), DetailTravel::getId);
		Long detailTravelId = detailTravelIdSet.get(0);
		clear();


		//when
		DetailTravelDto detailTravelDto = detailTravelService.getById(getLoginMemberId(), detailTravelId);

		//then
		assertThat(detailTravelDto.detailTravelId()).isEqualTo(detailTravelId);
		assertThat(detailTravelDto.title()).isEqualTo(detailTravelSaveDto.title());
		assertThat(detailTravelDto.review()).isEqualTo(detailTravelSaveDto.review());
		assertThat(detailTravelDto.tip()).isEqualTo(detailTravelSaveDto.tip());
		assertThat(detailTravelDto.visitedDate()).isEqualTo(detailTravelSaveDto.visitedDate());
		assertThat(detailTravelDto.address()).isEqualTo(detailTravelSaveDto.address());
		assertThat(detailTravelDto.createdAt()).isBefore(LocalDateTime.now().plusSeconds(2));
		assertThat(detailTravelDto.priceDtoList().size()).isEqualTo(detailTravelSaveDto.priceSaveDtoList().size());
		assertThat(detailTravelDto.imageDtoList().size()).isEqualTo(detailTravelSaveDto.imagePathList().size());
	}

	@Test
	@DisplayName("Main Travel 조회 (성공) [public 게시물, 미완성, 주인의 조회 요쳥]")
	public void successGetDetailTravelWherePublicAndUnCompleteAndRequestByOwner() throws Exception {

		//given
		MainTravelSaveDto mainTravelSaveDto = publicUnCompleteMainTravelSaveDto();
		DetailTravelSaveDto detailTravelSaveDto = mainTravelSaveDto.detailTravelSaveDtoList().get(0);
		Long mainTravelId = mainTravelService.saveMainTravel(getLoginMemberId(),mainTravelSaveDto);
		List<Long> detailTravelIdSet = mappingToList(detailTravelRepository.findAllByMainTravelId(mainTravelId), DetailTravel::getId);
		Long detailTravelId = detailTravelIdSet.get(0);
		clear();


		//when
		DetailTravelDto detailTravelDto = detailTravelService.getById(getLoginMemberId(), detailTravelId);

		//then
		assertThat(detailTravelDto.detailTravelId()).isEqualTo(detailTravelId);
		assertThat(detailTravelDto.title()).isEqualTo(detailTravelSaveDto.title());
		assertThat(detailTravelDto.review()).isEqualTo(detailTravelSaveDto.review());
		assertThat(detailTravelDto.tip()).isEqualTo(detailTravelSaveDto.tip());
		assertThat(detailTravelDto.visitedDate()).isEqualTo(detailTravelSaveDto.visitedDate());
		assertThat(detailTravelDto.address()).isEqualTo(detailTravelSaveDto.address());
		assertThat(detailTravelDto.createdAt()).isBefore(LocalDateTime.now().plusSeconds(2));
		assertThat(detailTravelDto.priceDtoList().size()).isEqualTo(detailTravelSaveDto.priceSaveDtoList().size());
		assertThat(detailTravelDto.imageDtoList().size()).isEqualTo(detailTravelSaveDto.imagePathList().size());
	}
	@Test
	@DisplayName("Main Travel 조회 (성공) [private 게시물, 미완성, 주인의 조회 요쳥]")
	public void successGetDetailTravelWherePrivateAndUnCompleteAndRequestByOwner() throws Exception {

		//given
		MainTravelSaveDto mainTravelSaveDto = privateUnCompleteMainTravelSaveDto();
		DetailTravelSaveDto detailTravelSaveDto = mainTravelSaveDto.detailTravelSaveDtoList().get(0);
		Long mainTravelId = mainTravelService.saveMainTravel(getLoginMemberId(),mainTravelSaveDto);
		List<Long> detailTravelIdSet = mappingToList(detailTravelRepository.findAllByMainTravelId(mainTravelId), DetailTravel::getId);
		Long detailTravelId = detailTravelIdSet.get(0);
		clear();


		//when
		DetailTravelDto detailTravelDto = detailTravelService.getById(getLoginMemberId(), detailTravelId);

		//then
		assertThat(detailTravelDto.detailTravelId()).isEqualTo(detailTravelId);
		assertThat(detailTravelDto.title()).isEqualTo(detailTravelSaveDto.title());
		assertThat(detailTravelDto.review()).isEqualTo(detailTravelSaveDto.review());
		assertThat(detailTravelDto.tip()).isEqualTo(detailTravelSaveDto.tip());
		assertThat(detailTravelDto.visitedDate()).isEqualTo(detailTravelSaveDto.visitedDate());
		assertThat(detailTravelDto.address()).isEqualTo(detailTravelSaveDto.address());
		assertThat(detailTravelDto.createdAt()).isBefore(LocalDateTime.now().plusSeconds(2));
		assertThat(detailTravelDto.priceDtoList().size()).isEqualTo(detailTravelSaveDto.priceSaveDtoList().size());
		assertThat(detailTravelDto.imageDtoList().size()).isEqualTo(detailTravelSaveDto.imagePathList().size());
	}

	@Test
	@DisplayName("Main Travel 조회 (성공) [public 게시물, 완성, 다른 사람의 조회 요쳥]")
	public void successGetDetailTravelWherePublicAndCompleteAndRequestByOther() throws Exception {
		MainTravelSaveDto mainTravelSaveDto = publicCompleteMainTravelSaveDto();
		DetailTravelSaveDto detailTravelSaveDto = mainTravelSaveDto.detailTravelSaveDtoList().get(0);
		Long mainTravelId = mainTravelService.saveMainTravel(getLoginMemberId(),mainTravelSaveDto);
		List<Long> detailTravelIdSet = mappingToList(detailTravelRepository.findAllByMainTravelId(mainTravelId), DetailTravel::getId);
		Long detailTravelId = detailTravelIdSet.get(0);
		clear();


		//when
		DetailTravelDto detailTravelDto = detailTravelService.getById(getLoginMemberId(), detailTravelId);

		//then
		assertThat(detailTravelDto.detailTravelId()).isEqualTo(detailTravelId);
		assertThat(detailTravelDto.title()).isEqualTo(detailTravelSaveDto.title());
		assertThat(detailTravelDto.review()).isEqualTo(detailTravelSaveDto.review());
		assertThat(detailTravelDto.tip()).isEqualTo(detailTravelSaveDto.tip());
		assertThat(detailTravelDto.visitedDate()).isEqualTo(detailTravelSaveDto.visitedDate());
		assertThat(detailTravelDto.address()).isEqualTo(detailTravelSaveDto.address());
		assertThat(detailTravelDto.createdAt()).isBefore(LocalDateTime.now().plusSeconds(2));
		assertThat(detailTravelDto.priceDtoList().size()).isEqualTo(detailTravelSaveDto.priceSaveDtoList().size());
		assertThat(detailTravelDto.imageDtoList().size()).isEqualTo(detailTravelSaveDto.imagePathList().size());
	}


	@Test
	@DisplayName("Main Travel 조회 (실패) [private 게시물, 완성, 다른 사람의 조회 요쳥]")
	public void failGetDetailTravelWherePrivateAndCompleteAndRequestByOther() throws Exception {

		//given
		MainTravelSaveDto mainTravelSaveDto = privateCompleteMainTravelSaveDto();
		DetailTravelSaveDto detailTravelSaveDto = mainTravelSaveDto.detailTravelSaveDtoList().get(0);
		Long mainTravelId = mainTravelService.saveMainTravel(getLoginMemberId(),mainTravelSaveDto);
		List<Long> detailTravelIdSet = mappingToList(detailTravelRepository.findAllByMainTravelId(mainTravelId), DetailTravel::getId);
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
		Long mainTravelId = mainTravelService.saveMainTravel(getLoginMemberId(),mainTravelSaveDto);
		List<Long> detailTravelIdSet = mappingToList(detailTravelRepository.findAllByMainTravelId(mainTravelId), DetailTravel::getId);
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
		Long mainTravelId = mainTravelService.saveMainTravel(getLoginMemberId(),mainTravelSaveDto);
		List<Long> detailTravelIdSet = mappingToList(detailTravelRepository.findAllByMainTravelId(mainTravelId), DetailTravel::getId);
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
		assertThat(assertThrows(DetailTravelException.class, () -> detailTravelService.getById(getLoginMemberId(), nullId)).getExceptionType())
			.isEqualTo(NOT_FOUND);

	}

	
}