package com.footprint.detailtravel.fixture;

import static com.footprint.maintravel.fixture.MainTravelFixture.*;
import static java.time.LocalDate.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import org.springframework.test.util.ReflectionTestUtils;

import com.footprint.detailtravel.controller.dto.request.CreateDetailTravelRequest;
import com.footprint.detailtravel.controller.dto.request.CreateImageRequest;
import com.footprint.detailtravel.controller.dto.request.CreatePriceRequest;
import com.footprint.detailtravel.controller.dto.response.DetailTravelInfoResponse;
import com.footprint.detailtravel.controller.dto.response.ImageInfoResponse;
import com.footprint.detailtravel.controller.dto.response.PriceInfoResponse;
import com.footprint.detailtravel.controller.dto.response.SimpleDetailTravelListResponse;
import com.footprint.detailtravel.controller.dto.response.SimpleDetailTravelResponse;
import com.footprint.detailtravel.domain.Address;
import com.footprint.detailtravel.domain.DetailTravel;
import com.footprint.detailtravel.service.dto.create.DetailTravelSaveDto;
import com.footprint.detailtravel.service.dto.create.ImageSaveDto;
import com.footprint.detailtravel.service.dto.create.PriceSaveDto;
import com.footprint.detailtravel.service.dto.info.DetailTravelDto;
import com.footprint.detailtravel.service.dto.info.ImageDto;
import com.footprint.detailtravel.service.dto.info.PriceDto;
import com.footprint.detailtravel.service.dto.info.SimpleDetailTravelDto;
import com.footprint.detailtravel.service.dto.info.SimpleDetailTravelListDto;
import com.footprint.image.domain.Image;
import com.footprint.price.domain.Price;

/**
 * Created by ShinD on 2022/06/26.
 */
public class DetailTravelFixture {


	private static final Long SIMPLE_DETAIL_TRAVEL_DTO_ID= 1L;
	private static final Long SIMPLE_DETAIL_TRAVEL_RESPONSE_ID = 1L;

	public static final Long DETAIL_TRAVEL_ID = 1L;
	private static final Long MAIN_TRAVEL_ID = 1L;

	private static final String TITLE = "title";
	private static final String UPDATE_TITLE = "update-title";

	private static final String REVIEW = "review";
	private static final String UPDATE_REVIEW = "update-review";

	private static final String TIP = "tip";
	private static final String UPDATE_TIP = "update-tip";

	private static final String VISITED_DATE_STRING = "2022-06-15";
	private static final LocalDate VISITED_DATE = parse(VISITED_DATE_STRING);

	private static final String UPDATE_VISITED_DATE_STRING = "2022-06-15";
	private static final LocalDate UPDATE_VISITED_DATE = parse(UPDATE_VISITED_DATE_STRING);

	private static final Address ADDRESS = Address.builder().roadAddress("roadAddress").mapX(1).mapY(2).address("address").build();
	private static final Address UPDATE_ADDRESS = Address.builder().roadAddress("(update)roadAddress").mapX(21).mapY(22).address("(update)address").build();

	private static final String CREATED_AT_STRING = "2022-06-18T00:00";
	private static final LocalDateTime CREATED_AT = LocalDateTime.parse(CREATED_AT_STRING, DateTimeFormatter.ISO_LOCAL_DATE_TIME);


	//TODO Price랑 Image 구현 후 Fixture로 분리
	private static final List<PriceSaveDto> PRICE_SAVE_DTO_LIST = new ArrayList<>();
	private static final List<ImageSaveDto> IMAGE_SAVE_DTO_LIST = new ArrayList<>();

	private static final List<PriceDto> PRICE_DTO_LIST = new ArrayList<>();
	private static final List<ImageDto> IMAGE_DTO_LIST = new ArrayList<>();

	private static final List<PriceInfoResponse> PRICE_INFO_RESPONSES_LIST = new ArrayList<>();
	private static final List<ImageInfoResponse> IMAGE_INFO_RESPONSE_LIST = new ArrayList<>();

	private static final List<CreatePriceRequest> CREATE_PRICE_REQUEST_LIST = new ArrayList<>();
	private static final List<CreateImageRequest> IMAGE_PATH_LIST = new ArrayList<>();

	private static final List<Image> IMAGE_LIST = new ArrayList<>();
	private static final List<Price> PRICE_LIST = new ArrayList<>();





	public static DetailTravelSaveDto detailTravelSaveDto(int detailTravelCount) {
		return new DetailTravelSaveDto(
			title(detailTravelCount),
			review(detailTravelCount),
			tip(detailTravelCount),
			VISITED_DATE,
			ADDRESS,
			PRICE_SAVE_DTO_LIST,
			IMAGE_SAVE_DTO_LIST
		);
	}

	public static DetailTravelSaveDto updateDetailTravelSaveDto( int detailTravelCount) {
		return new DetailTravelSaveDto(
			updateTitle(detailTravelCount),
			updateReview(detailTravelCount),
			updateTip(detailTravelCount),
			UPDATE_VISITED_DATE,
			UPDATE_ADDRESS,
			PRICE_SAVE_DTO_LIST,
			IMAGE_SAVE_DTO_LIST
		);
	}


	public static CreateDetailTravelRequest createDetailTravelRequest(int detailTravelCount) {
		return new CreateDetailTravelRequest(
			title(detailTravelCount),
			review(detailTravelCount),
			tip(detailTravelCount),
			VISITED_DATE_STRING,
			ADDRESS,
			CREATE_PRICE_REQUEST_LIST,
			IMAGE_PATH_LIST
		);
	}


	public static SimpleDetailTravelDto simpleDetailTravelDto(Long mainTravelId, int detailTravelCount) {
		System.out.println(CREATED_AT.toString());
		return new SimpleDetailTravelDto(
			detailTravelCount + 1L,
			mainTravelId,
			title(detailTravelCount),
			review(detailTravelCount),
			VISITED_DATE,
			ADDRESS,
			CREATED_AT
		);
	}

	public static SimpleDetailTravelListDto simpleDetailTravelListDto(Long mainTravelId, int count) {
		return new SimpleDetailTravelListDto(
			count,
			IntStream.range(0, count).mapToObj( i-> simpleDetailTravelDto(mainTravelId, i)).toList()
		);
	}


	public static SimpleDetailTravelResponse simpleDetailTravelResponse(Long mainTravelId, int detailTravelId) {

		return new SimpleDetailTravelResponse(
			detailTravelId + 1L,
			mainTravelId,
			title(detailTravelId),
			review(detailTravelId),
			VISITED_DATE_STRING,
			ADDRESS,
			CREATED_AT_STRING
		);
	}

	public static SimpleDetailTravelListResponse simpleDetailTravelListResponse(Long mainTravelId, int count) {
		return new SimpleDetailTravelListResponse(
			count,
			IntStream.range(0, count).mapToObj(i -> simpleDetailTravelResponse(mainTravelId, i)).toList()
		);
	}


	public static DetailTravel detailTravelHasId() {
		DetailTravel detailTravel = DetailTravel.builder().title(TITLE).review(REVIEW).tip(TIP).visitedDate(VISITED_DATE).address(ADDRESS).build();
		ReflectionTestUtils.setField(detailTravel, "id", DETAIL_TRAVEL_ID);
		detailTravel.setMainTravel(mainTravelOnlyHasId());
		detailTravel.setImages(IMAGE_LIST);
		detailTravel.setPrices(PRICE_LIST);
		return detailTravel;
	}


	public static DetailTravelInfoResponse detailTravelInfoResponse() {
		return new DetailTravelInfoResponse(
			DETAIL_TRAVEL_ID,
			MAIN_TRAVEL_ID,
			TITLE,
			REVIEW,
			TIP,
			VISITED_DATE_STRING,
			ADDRESS,
			CREATED_AT_STRING,
			PRICE_INFO_RESPONSES_LIST,
			IMAGE_INFO_RESPONSE_LIST
		);
	}

	public static DetailTravelDto detailTravelDto() {
		return new DetailTravelDto(
			DETAIL_TRAVEL_ID,
			MAIN_TRAVEL_ID,
			TITLE,
			REVIEW,
			TIP,
			VISITED_DATE,
			ADDRESS,
			CREATED_AT,
			PRICE_DTO_LIST,
			IMAGE_DTO_LIST
		);
	}





	private static String tip(int detailTravelCount) {
		return "[tip] %s (%d)".formatted(TIP, detailTravelCount);
	}

	private static String review(int detailTravelCount) {
		return "[review] %s (%d)".formatted(REVIEW, detailTravelCount);
	}

	private static String title(int detailTravelCount) {
		return "[title] %s (%d)".formatted(TITLE,detailTravelCount);
	}

	private static String updateTitle(int detailTravelCount) {
		return "[title](update) %s (%d)".formatted(UPDATE_TITLE,detailTravelCount);
	}

	private static String updateReview(int detailTravelCount) {
		return "[review](update) %s (%d)".formatted(UPDATE_REVIEW,detailTravelCount);
	}

	private static String updateTip(int detailTravelCount) {
		return "[tip](update) %s (%d)".formatted(UPDATE_TIP,detailTravelCount);
	}

}
