package com.footprint.detailtravel.controller.dto.request;

import static com.footprint.detailtravel.fixture.DetailTravelFixture.*;
import static com.footprint.util.MappingTestUtil.*;
import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.footprint.detailtravel.domain.Address;
import com.footprint.detailtravel.service.dto.create.DetailTravelSaveDto;
import com.footprint.detailtravel.service.dto.create.ImageSaveDto;
import com.footprint.detailtravel.service.dto.create.PriceSaveDto;

/**
 * Created by ShinD on 2022/06/26.
 */
class CreateDetailTravelRequestTest {
	//TODO Price, Image 구현 후 추가하기
	private final ObjectMapper objectMapper = new ObjectMapper();
	private static final String REQUEST_BODY = """
		{
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
		}
		""";


	@Test
	@DisplayName("Json -> CreateDetailTravelRequest 변환 테스트")
	void jsonToCreateMainTravelRequestTest() throws Exception {
		//given, when
		CreateDetailTravelRequest createDetailTravelRequest = objectMapper.readValue(REQUEST_BODY, CreateDetailTravelRequest.class);

		//then
		assertThat(createDetailTravelRequest.title()).isEqualTo("detailTitle 1");
		assertThat(createDetailTravelRequest.review()).isEqualTo("detailReview 1");
		assertThat(createDetailTravelRequest.tip()).isEqualTo("detailTip 1");
		assertThat(createDetailTravelRequest.visitedDate()).isEqualTo("2022-03-15");
		assertThat(createDetailTravelRequest.address()).isEqualTo(Address.builder().address("address1").roadAddress("roadAddress1").mapX(123).mapY(345).build());
		assertThat(createDetailTravelRequest.createPriceRequestList()).isEmpty();
		assertThat(createDetailTravelRequest.createImageRequestList().size()).isEqualTo(3);

	}


	@Test
	@DisplayName("CreateDetailTravelRequest -> DetailTravelSaveDto 변환 테스트")
	void createMainTravelRequestToMainTravelSaveDtoTest() throws Exception {
		//given
		CreateDetailTravelRequest detailTravelRequest = createDetailTravelRequest(1);


		//when
		DetailTravelSaveDto detailTravelSaveDto = detailTravelRequest.toServiceDto();

		//then
		assertThat(detailTravelSaveDto.title()).isEqualTo(detailTravelRequest.title());
		assertThat(detailTravelSaveDto.review()).isEqualTo(detailTravelRequest.review());
		assertThat(detailTravelSaveDto.tip()).isEqualTo(detailTravelRequest.tip());
		assertThat(detailTravelSaveDto.visitedDate()).isEqualTo(detailTravelRequest.visitedDate());
		assertThat(detailTravelSaveDto.address()).isEqualTo(detailTravelRequest.address());

		List<PriceSaveDto> priceSaveDtos = detailTravelSaveDto.priceSaveDtoList();
		List<CreatePriceRequest> priceRequestList = detailTravelRequest.createPriceRequestList();
		for (int i = 0; i < priceRequestList.size(); i++) {
			PriceSaveDto priceSaveDto = priceSaveDtos.get(i);
			CreatePriceRequest createPriceRequest = priceRequestList.get(i);
			assertThat(priceSaveDto.item()).isEqualTo(createPriceRequest.item());
			assertThat(priceSaveDto.priceInfo()).isEqualTo(createPriceRequest.price());
		}

		List<ImageSaveDto> imageSaveDtos = detailTravelSaveDto.imageSaveDtoList();
		List<CreateImageRequest> imageRequestList = detailTravelRequest.createImageRequestList();
		for (int i = 0; i < imageRequestList.size(); i++) {
			ImageSaveDto imageSaveDto = imageSaveDtos.get(i);
			CreateImageRequest createImageRequest = imageRequestList.get(i);
			assertThat(imageSaveDto.path()).isEqualTo(createImageRequest.path());
		}
	}
}