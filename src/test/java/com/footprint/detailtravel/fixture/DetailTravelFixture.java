package com.footprint.detailtravel.fixture;

import static com.footprint.image.fixture.ImageFixture.*;
import static com.footprint.maintravel.fixture.MainTravelFixture.*;
import static com.footprint.price.fixture.PriceFixture.*;
import static java.time.LocalDate.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.stream.IntStream;

import org.springframework.test.util.ReflectionTestUtils;

import com.footprint.detailtravel.controller.dto.request.CreateDetailTravelRequest;
import com.footprint.detailtravel.controller.dto.response.DetailTravelInfoResponse;
import com.footprint.detailtravel.controller.dto.response.SimpleDetailTravelListResponse;
import com.footprint.detailtravel.controller.dto.response.SimpleDetailTravelResponse;
import com.footprint.detailtravel.domain.Address;
import com.footprint.detailtravel.domain.DetailTravel;
import com.footprint.detailtravel.service.dto.create.DetailTravelSaveDto;
import com.footprint.detailtravel.service.dto.info.DetailTravelDto;
import com.footprint.detailtravel.service.dto.info.SimpleDetailTravelDto;
import com.footprint.detailtravel.service.dto.info.SimpleDetailTravelListDto;

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







	public static DetailTravelSaveDto detailTravelSaveDto(int detailTravelCount) {
		return new DetailTravelSaveDto(
			title(detailTravelCount),
			review(detailTravelCount),
			tip(detailTravelCount),
			VISITED_DATE,
			ADDRESS,
			priceSaveDtoList(3),
			imageSaveDtoList(3)
		);
	}

	public static DetailTravelSaveDto updateDetailTravelSaveDto( int detailTravelCount) {
		return new DetailTravelSaveDto(
			updateTitle(detailTravelCount),
			updateReview(detailTravelCount),
			updateTip(detailTravelCount),
			UPDATE_VISITED_DATE,
			UPDATE_ADDRESS,
			priceSaveDtoList(3),
			imageSaveDtoList(3)
		);
	}


	public static CreateDetailTravelRequest createDetailTravelRequest(int detailTravelCount) {
		return new CreateDetailTravelRequest(
			title(detailTravelCount),
			review(detailTravelCount),
			tip(detailTravelCount),
			VISITED_DATE_STRING,
			ADDRESS,
			createPriceRequestList(3),
			createImageRequestList(3)
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
		detailTravel.setImages(imageList(3));
		detailTravel.setPrices(priceList(3));
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
			priceInfoResponseList(3),
			imageInfoResponseList(3)
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
			priceDtoList(3),
			imageDtoList(3)
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
