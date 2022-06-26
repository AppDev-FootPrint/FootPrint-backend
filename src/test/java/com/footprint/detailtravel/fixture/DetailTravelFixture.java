package com.footprint.detailtravel.fixture;

import static java.time.LocalDate.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import org.springframework.test.util.ReflectionTestUtils;

import com.footprint.detailtravel.controller.dto.request.CreateDetailTravelRequest;
import com.footprint.detailtravel.controller.dto.request.CreatePriceRequest;
import com.footprint.detailtravel.controller.dto.response.SimpleDetailTravelListResponse;
import com.footprint.detailtravel.controller.dto.response.SimpleDetailTravelResponse;
import com.footprint.detailtravel.domain.Address;
import com.footprint.detailtravel.domain.DetailTravel;
import com.footprint.detailtravel.service.dto.create.DetailTravelSaveDto;
import com.footprint.detailtravel.service.dto.create.PriceSaveDto;
import com.footprint.detailtravel.service.dto.info.SimpleDetailTravelDto;
import com.footprint.detailtravel.service.dto.info.SimpleDetailTravelListDto;
import com.footprint.image.domain.Image;
import com.footprint.price.domain.Price;

/**
 * Created by ShinD on 2022/06/26.
 */
public class DetailTravelFixture {
	private static int detailTravelCount = 1;

	private static Long simpleDetailTravelDtoId= 0L;
	private static Long simpleDetailTravelResponseId = 0L;

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

	private static final String CREATED_AT_STRING = "2022-06-18T00:00:00";
	private static final LocalDateTime CREATED_AT = LocalDateTime.parse(CREATED_AT_STRING, DateTimeFormatter.ISO_LOCAL_DATE_TIME);

	private static final List<PriceSaveDto> PRICE_SAVE_DTO_LIST = new ArrayList<>();
	private static final List<CreatePriceRequest> CREATE_PRICE_REQUEST_LIST = new ArrayList<>();
	private static final List<String> IMAGE_PATH_LIST = new ArrayList<>();

	private static final List<Image> IMAGE_LIST = new ArrayList<>();
	private static final List<Price> PRICE_LIST = new ArrayList<>();





	public static DetailTravelSaveDto detailTravelSaveDto(int mtCnt) {
		detailTravelCount++;
		return new DetailTravelSaveDto(
			title(mtCnt),
			review(mtCnt),
			tip(mtCnt),
			VISITED_DATE,
			ADDRESS,
			PRICE_SAVE_DTO_LIST,
			IMAGE_PATH_LIST
		);
	}

	public static DetailTravelSaveDto updateDetailTravelSaveDto(int mainTravelCount) {
		return new DetailTravelSaveDto(
			updateTitle(mainTravelCount),
			updateReview(mainTravelCount),
			updateTip(mainTravelCount),
			UPDATE_VISITED_DATE,
			UPDATE_ADDRESS,
			PRICE_SAVE_DTO_LIST,
			IMAGE_PATH_LIST
		);
	}


	public static CreateDetailTravelRequest createDetailTravelRequest(int mainTravelCount) {
		return new CreateDetailTravelRequest(
			title(mainTravelCount),
			review(mainTravelCount),
			tip(mainTravelCount),
			VISITED_DATE_STRING,
			ADDRESS,
			CREATE_PRICE_REQUEST_LIST,
			IMAGE_PATH_LIST
		);
	}


	public static SimpleDetailTravelDto simpleDetailTravelDto(int idx) {
		simpleDetailTravelDtoId++;
		return new SimpleDetailTravelDto(simpleDetailTravelDtoId,
			title(idx),
			review(idx),
			VISITED_DATE,
			ADDRESS,
			CREATED_AT
		);
	}

	public static SimpleDetailTravelListDto simpleDetailTravelListDto(int count) {
		return new SimpleDetailTravelListDto(
			count,
			IntStream.range(0, count).mapToObj(DetailTravelFixture::simpleDetailTravelDto).toList()
		);
	}


	public static SimpleDetailTravelResponse simpleDetailTravelResponse(int idx) {
		simpleDetailTravelResponseId++;
		return new SimpleDetailTravelResponse(simpleDetailTravelResponseId,
			title(idx),
			review(idx),
			VISITED_DATE_STRING,
			ADDRESS,
			CREATED_AT_STRING
		);
	}

	public static SimpleDetailTravelListResponse simpleDetailTravelListResponse(int count) {
		return new SimpleDetailTravelListResponse(
			count,
			IntStream.range(0, count).mapToObj(DetailTravelFixture::simpleDetailTravelResponse).toList()
		);
	}


	public static DetailTravel detailTravelHasId() {
		DetailTravel detailTravel = DetailTravel.builder().title(TITLE).review(REVIEW).tip(TIP).visitedDate(VISITED_DATE).address(ADDRESS).build();
		ReflectionTestUtils.setField(detailTravel, "id", 1L);
		detailTravel.setImages(IMAGE_LIST);
		detailTravel.setPrices(PRICE_LIST);
		return DetailTravel.builder().build();
	}




	private static String tip(int mainTravelCount) {
		return "[tip] %s (%d - %d)".formatted(TIP, mainTravelCount, detailTravelCount);
	}

	private static String review(int mainTravelCount) {
		return "[review] %s (%d - %d)".formatted(REVIEW, mainTravelCount, detailTravelCount);
	}

	private static String title(int mainTravelCount) {
		return "[title] : %s (%d - %d)".formatted(TITLE, mainTravelCount,detailTravelCount);
	}

	private static String updateTitle(int mainTravelCount) {
		return "[title](update) %s (%d - %d)".formatted(UPDATE_TITLE, mainTravelCount,detailTravelCount);
	}

	private static String updateReview(int mainTravelCount) {
		return "[review](update) %s (%d - %d)".formatted(UPDATE_REVIEW, mainTravelCount,detailTravelCount);
	}

	private static String updateTip(int mainTravelCount) {
		return "[tip](update) %s (%d - %d)".formatted(UPDATE_TIP, mainTravelCount,detailTravelCount);
	}
}
