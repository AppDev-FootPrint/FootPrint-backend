package com.footprint.maintravel.controller.dto.request.update;

import static com.footprint.maintravel.fixture.MainTravelFixture.*;
import static com.footprint.util.MappingTestUtil.*;
import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.footprint.detailtravel.controller.dto.request.CreateDetailTravelRequest;

import com.footprint.detailtravel.domain.Address;
import com.footprint.detailtravel.service.dto.create.DetailTravelSaveDto;
import com.footprint.image.controller.dto.CreateImageRequest;
import com.footprint.maintravel.service.dto.update.MainTravelUpdateDto;
import com.footprint.price.controller.dto.CreatePriceRequest;


/**
 * Created by ShinD on 2022/06/26.
 */
class UpdateMainTravelRequestTest {

	private final ObjectMapper objectMapper = new ObjectMapper();
	private static final String REQUEST_BODY = """
		{
   			"title" : "mainTitle",
   			"startDate" : "2022-03-15",
   			"endDate" : "2022-03-18",
   			"isVisible" : "true",
   			"isCompleted" : "true",
   			"mainImagePath" : "https://ttl-blog.tistory.com/",
   			"createDetailTravelRequest" : [{
   				"title" : "detailTitle 1",
   			  	"review" : "detailReview 1",
   			  	"tip" : "detailTip 1",
   			  	"visitedDate" : "2022-03-15",
   			  	"address" : {  "address" : "address1",  "roadAddress" : "roadAddress1",   "mapX" : "123",  "mapY" : "345"},
   		 		
   		 		"createPriceRequestList" : [],
   			  	"createImageRequestList" : [
   						{"path" : "https://ttl-blog.tistory.com/277?category=906283"},
   						{"path" : "https://ttl-blog.tistory.com/277?category=906284"},
   						{"path" : "https://ttl-blog.tistory.com/277?category=906285"}
   			  		]
   			},
   			{
   				"title" : "detailTitle 2",
   			  	"review" : "detailReview 2",
   			  	"tip" :"detailTip 2",
   			  	"visitedDate" : "2022-03-16",
   			  	"address" : {  "address" : "address2",  "roadAddress" : "roadAddress2",   "mapX" : "22223",  "mapY" : "222345"},
   			  	"createPriceRequestList" : [],
   			  	"createImageRequestList" : []
   			}]
   		}
   		""";


	@Test
	@DisplayName("Json -> UpdateMainTravelRequest 변환 테스트")
	void jsonToUpdateMainTravelRequestTest() throws Exception {
		//given, when
		UpdateMainTravelRequest updateMainTravelRequest = objectMapper.readValue(REQUEST_BODY, UpdateMainTravelRequest.class);

		//then
		assertThat(updateMainTravelRequest.title()).isEqualTo("mainTitle");
		assertThat(updateMainTravelRequest.startDate()).isEqualTo("2022-03-15");
		assertThat(updateMainTravelRequest.endDate()).isEqualTo("2022-03-18");
		assertThat(updateMainTravelRequest.isVisible()).isTrue();
		assertThat(updateMainTravelRequest.isCompleted()).isTrue();
		assertThat(updateMainTravelRequest.mainImagePath()).isEqualTo("https://ttl-blog.tistory.com/");
		assertThat(updateMainTravelRequest.createDetailTravelRequest().size()).isEqualTo(2);




		CreateDetailTravelRequest createDetailTravelRequest1 = updateMainTravelRequest.createDetailTravelRequest().get(0);
		CreateDetailTravelRequest createDetailTravelRequest2 = updateMainTravelRequest.createDetailTravelRequest().get(1);

		assertThat(createDetailTravelRequest1.title()).isEqualTo("detailTitle 1");
		assertThat(createDetailTravelRequest1.review()).isEqualTo("detailReview 1");
		assertThat(createDetailTravelRequest1.tip()).isEqualTo("detailTip 1");
		assertThat(createDetailTravelRequest1.visitedDate()).isEqualTo("2022-03-15");
		assertThat(createDetailTravelRequest1.address()).isEqualTo(
			Address.builder().address("address1").roadAddress("roadAddress1").mapX(123).mapY(345).build());
		assertThat(createDetailTravelRequest2.createPriceRequestList()).isEmpty();
		assertThat(createDetailTravelRequest1.createImageRequestList().size()).isEqualTo(3);


		List<String> paths = mappingToList(
											createDetailTravelRequest1.createImageRequestList(),
											createImageRequest -> createImageRequest.toServiceDto().path());
		assertThat(paths).contains("https://ttl-blog.tistory.com/277?category=906283");
		assertThat(paths).contains("https://ttl-blog.tistory.com/277?category=906284");
		assertThat(paths).contains("https://ttl-blog.tistory.com/277?category=906285");



		assertThat(createDetailTravelRequest2.title()).isEqualTo("detailTitle 2");
		assertThat(createDetailTravelRequest2.review()).isEqualTo("detailReview 2");
		assertThat(createDetailTravelRequest2.tip()).isEqualTo("detailTip 2");
		assertThat(createDetailTravelRequest2.visitedDate()).isEqualTo("2022-03-16");
		assertThat(createDetailTravelRequest2.address()).isEqualTo(Address.builder().address("address2").roadAddress("roadAddress2").mapX(22223).mapY(222345).build());
		assertThat(createDetailTravelRequest2.createPriceRequestList()).isEmpty();
		assertThat(createDetailTravelRequest2.createPriceRequestList()).isEmpty();


	}

	@Test
	@DisplayName("UpdateMainTravelRequest -> MainTravelUpdateDto 변환 테스트")
	void updateMainTravelRequestToMainTravelUpdateDtoTest() throws Exception {
		//given
		UpdateMainTravelRequest mainTravelRequest = updateMainTravelRequest();

		//when
		MainTravelUpdateDto mainTravelUpdateDto = mainTravelRequest.toServiceDto(1L);


		//then
		assertThat(mainTravelUpdateDto.id()).isEqualTo(1L);
		assertThat(mainTravelUpdateDto.title()).isEqualTo(mainTravelRequest.title());
		assertThat(mainTravelUpdateDto.startDate()).isEqualTo(mainTravelRequest.startDate());
		assertThat(mainTravelUpdateDto.endDate()).isEqualTo(mainTravelRequest.endDate());
		assertThat(mainTravelUpdateDto.isVisible()).isEqualTo(mainTravelRequest.isVisible());
		assertThat(mainTravelUpdateDto.isCompleted()).isEqualTo(mainTravelRequest.isCompleted());
		assertThat(mainTravelUpdateDto.mainImagePath()).isEqualTo(mainTravelRequest.mainImagePath());
		assertThat(mainTravelUpdateDto.detailTravelSaveDtoList().size()).isEqualTo(mainTravelRequest.createDetailTravelRequest().size());

		for (int i = 0; i < mainTravelRequest.createDetailTravelRequest().size(); i++) {
			DetailTravelSaveDto detailTravelSaveDto1 = mainTravelUpdateDto.detailTravelSaveDtoList().get(i);
			CreateDetailTravelRequest createDetailTravelRequest = mainTravelRequest.createDetailTravelRequest().get(i);

			assertThat(detailTravelSaveDto1.title()).isEqualTo(createDetailTravelRequest.title());
			assertThat(detailTravelSaveDto1.review()).isEqualTo(createDetailTravelRequest.review());
			assertThat(detailTravelSaveDto1.tip()).isEqualTo(createDetailTravelRequest.tip());
			assertThat(detailTravelSaveDto1.visitedDate()).isEqualTo(
				LocalDate.parse(createDetailTravelRequest.visitedDate(), DateTimeFormatter.ISO_LOCAL_DATE));
			assertThat(detailTravelSaveDto1.address()).isEqualTo(createDetailTravelRequest.address());
			assertThat(detailTravelSaveDto1.priceSaveDtoList())
				.isEqualTo(mappingToList(createDetailTravelRequest.createPriceRequestList(), CreatePriceRequest::toServiceDto));

			assertThat(detailTravelSaveDto1.imageSaveDtoList())
				.isEqualTo(mappingToList(createDetailTravelRequest.createImageRequestList(), CreateImageRequest::toServiceDto));
		}


	}
}