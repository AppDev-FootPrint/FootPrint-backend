
package com.footprint.maintravel.fixture;

import static com.footprint.detailtravel.fixture.DetailTravelFixture.*;
import static java.time.LocalDate.*;
import static java.util.stream.IntStream.*;

import java.time.LocalDate;

import com.footprint.detailtravel.service.dto.create.ImageSaveDto;
import com.footprint.maintravel.service.dto.save.MainTravelSaveDto;
import com.footprint.maintravel.service.dto.update.MainTravelUpdateDto;

public class MainTravelFixture {


	//동시성 문제??
	private static int mainTravelCount = 0;

	private static final String TITLE = "title";
	private static final String UPDATE_TITLE = "update-title";

	private static final String IMAGE_PATH = "https://ttl-blog.tistory.com/";
	private static final String UPDATE_IMAGE_PATH = "https://ttl-blog.tistory.com/474?category=910686";

	private static final String START_DATE_STRING = "2022-06-15";
	private static final LocalDate START_DATE = parse(START_DATE_STRING);

	private static final String UPDATE_START_DATE_STRING = "2022-05-15";
	private static final LocalDate UPDATE_START_DATE = parse(UPDATE_START_DATE_STRING);

	private static final String END_DATE_STRING = "2022-06-17";
	private static final LocalDate END_DATE = parse(END_DATE_STRING);

	private static final String UPDATE_END_DATE_STRING = "2022-05-19";
	private static final LocalDate UPDATE_END_DATE = parse(UPDATE_END_DATE_STRING);

	private static final boolean TRUE_VISIBLE = true;
	private static final boolean FALSE_VISIBLE = false;
	private static final boolean TRUE_COMPLETE = true;
	private static final boolean FALSE_COMPLETE = false;



	private static String title() {
		return TITLE + mainTravelCount;
	}
	private static String updateTitle() {
		return UPDATE_TITLE + mainTravelCount;
	}

	public static MainTravelSaveDto mainTravelSaveDto() {
		mainTravelCount ++;
		return new MainTravelSaveDto(
			title(),
			START_DATE,
			END_DATE,
			TRUE_VISIBLE,
			TRUE_COMPLETE,
			IMAGE_PATH,
			rangeClosed(0, 9).mapToObj(i -> detailTravelSaveDto(mainTravelCount)).toList()
		);
	}



	public static MainTravelUpdateDto mainTravelUpdateDto(Long mainTravelId) {
		return new MainTravelUpdateDto(
			mainTravelId,
			updateTitle(),
			UPDATE_START_DATE,
			UPDATE_END_DATE,
			FALSE_VISIBLE,
			FALSE_COMPLETE,
			UPDATE_IMAGE_PATH,
			rangeClosed(0, 11).mapToObj(i -> updateDetailTravelSaveDto(mainTravelCount)).toList()
			);
	}


	public static MainTravelSaveDto publicCompleteMainTravelSaveDto() {
		mainTravelCount ++;
		return new MainTravelSaveDto(
			title(),
			START_DATE,
			END_DATE,
			TRUE_VISIBLE,
			TRUE_COMPLETE,
			IMAGE_PATH,
			rangeClosed(0, 9).mapToObj(i -> detailTravelSaveDto(mainTravelCount)).toList()
		);
	}

	public static MainTravelSaveDto privateCompleteMainTravelSaveDto() {
		mainTravelCount ++;
		return new MainTravelSaveDto(
			title(),
			START_DATE,
			END_DATE,
			FALSE_VISIBLE,
			TRUE_COMPLETE,
			IMAGE_PATH,
			rangeClosed(0, 9).mapToObj(i -> detailTravelSaveDto(mainTravelCount)).toList()
		);
	}
	public static MainTravelSaveDto publicUnCompleteMainTravelSaveDto() {
		mainTravelCount ++;
		return new MainTravelSaveDto(
			title(),
			START_DATE,
			END_DATE,
			TRUE_VISIBLE,
			FALSE_COMPLETE,
			IMAGE_PATH,
			rangeClosed(0, 9).mapToObj(i -> detailTravelSaveDto(mainTravelCount)).toList()
		);
	}
	public static MainTravelSaveDto privateUnCompleteMainTravelSaveDto() {
		mainTravelCount ++;
		return new MainTravelSaveDto(
			title(),
			START_DATE,
			END_DATE,
			FALSE_VISIBLE,
			FALSE_COMPLETE,
			IMAGE_PATH,
			rangeClosed(0, 9).mapToObj(i -> detailTravelSaveDto(mainTravelCount)).toList()
		);
	}



}
