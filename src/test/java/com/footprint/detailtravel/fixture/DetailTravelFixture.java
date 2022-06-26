package com.footprint.detailtravel.fixture;

import static java.time.LocalDate.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.footprint.detailtravel.domain.Address;
import com.footprint.detailtravel.service.dto.create.DetailTravelSaveDto;
import com.footprint.detailtravel.service.dto.create.ImageSaveDto;
import com.footprint.detailtravel.service.dto.create.PriceSaveDto;

/**
 * Created by ShinD on 2022/06/26.
 */
public class DetailTravelFixture {
	private static int detailTravelCount = 1;

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

	private static final LocalDateTime CREATED_AT = LocalDateTime.now();
	private static final String CREATED_AT_STRING = CREATED_AT.toString();

	private static final List<PriceSaveDto> PRICE_SAVE_DTO_LIST = new ArrayList<>();
	private static final List<String> IMAGE_SAVE_DTO_LIST = new ArrayList<>();



	public static DetailTravelSaveDto detailTravelSaveDto(int mtCnt) {
		detailTravelCount++;
		return new DetailTravelSaveDto(
			title(mtCnt),
			review(mtCnt),
			tip(mtCnt),
			VISITED_DATE,
			ADDRESS,
			PRICE_SAVE_DTO_LIST,
			IMAGE_SAVE_DTO_LIST
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
			IMAGE_SAVE_DTO_LIST
		);
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
