
package com.footprint.maintravel.fixture;

import static com.footprint.detailtravel.fixture.DetailTravelFixture.*;
import static com.footprint.member.fixture.MemberFixture.*;
import static java.time.LocalDate.*;
import static java.time.format.DateTimeFormatter.*;
import static java.util.stream.IntStream.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.stream.IntStream;

import org.springframework.test.util.ReflectionTestUtils;

import com.footprint.detailtravel.fixture.DetailTravelFixture;
import com.footprint.maintravel.controller.dto.request.create.CreateMainTravelRequest;
import com.footprint.maintravel.controller.dto.request.update.UpdateMainTravelRequest;
import com.footprint.maintravel.controller.dto.response.MainTravelInfoResponse;
import com.footprint.maintravel.domain.MainTravel;
import com.footprint.maintravel.service.dto.info.MainTravelInfoDto;
import com.footprint.maintravel.service.dto.save.MainTravelSaveDto;
import com.footprint.maintravel.service.dto.update.MainTravelUpdateDto;

public class MainTravelFixture {


	private static int mainTravelCount = 0;
	public static final Long MAIN_TRAVEL_ID = 1L;

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


	private static final String CREATED_AT_STRING = "2022-06-22T00:00:00";
	private static final LocalDateTime CREATED_AT = LocalDateTime.parse(CREATED_AT_STRING, ISO_LOCAL_DATE_TIME);

	private static final int LIKE_NUM = 0;







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

	public static CreateMainTravelRequest createMainTravelRequest() {
		return new CreateMainTravelRequest(
			TITLE,
			START_DATE_STRING,
			END_DATE_STRING,
			TRUE_VISIBLE,
			TRUE_COMPLETE,
			IMAGE_PATH,
			rangeClosed(0, 9).mapToObj(DetailTravelFixture::createDetailTravelRequest).toList());
	}

	public static UpdateMainTravelRequest updateMainTravelRequest() {
		return new UpdateMainTravelRequest(
			TITLE,
			START_DATE_STRING,
			END_DATE_STRING,
			TRUE_VISIBLE,
			TRUE_COMPLETE,
			IMAGE_PATH,
			rangeClosed(0, 9).mapToObj(DetailTravelFixture::createDetailTravelRequest).toList()
		);
	}



	public static MainTravel mainTravelHasId() {
		MainTravel mainTravel = MainTravel.builder()
			.title(TITLE)
			.startDate(START_DATE)
			.endDate(END_DATE)
			.isVisible(TRUE_VISIBLE)
			.isCompleted(TRUE_COMPLETE)
			.build();

		ReflectionTestUtils.setField(mainTravel, "createdAt", CREATED_AT);
		ReflectionTestUtils.setField(mainTravel, "id", MAIN_TRAVEL_ID);
		mainTravel.setDetailTravels(IntStream.rangeClosed(0,2).mapToObj(i -> detailTravelHasId()).toList());
		mainTravel.setDetailTravels(IntStream.rangeClosed(0,2).mapToObj(i -> detailTravelHasId()).toList());
		mainTravel.setImage(IMAGE_PATH);
		mainTravel.setWriter(memberHasId());
		return mainTravel;
	}


	public static MainTravelInfoDto mainTravelInfoDto() {
		return new MainTravelInfoDto(
			MAIN_TRAVEL_ID,
			memberInfoDto(),
			TITLE,
			START_DATE_STRING,
			END_DATE_STRING,
			CREATED_AT_STRING,
			TRUE_VISIBLE,
			TRUE_COMPLETE,
			IMAGE_PATH,
			LIKE_NUM,
			simpleDetailTravelListDto(MAIN_TRAVEL_ID, 3)
		);
	}


	public static MainTravelInfoResponse mainTravelInfoResponse() {
		return new MainTravelInfoResponse(
			MAIN_TRAVEL_ID,
			memberInfoResponse(),
			TITLE,
			START_DATE_STRING,
			END_DATE_STRING,
			CREATED_AT_STRING,
			TRUE_VISIBLE,
			TRUE_COMPLETE,
			IMAGE_PATH,
			LIKE_NUM,
			simpleDetailTravelListResponse(MAIN_TRAVEL_ID, 3)
		);
	}

	public static MainTravel mainTravelOnlyHasId() {
		MainTravel mainTravel = MainTravel.builder().build();
		ReflectionTestUtils.setField(mainTravel, "id", 1L);
		return mainTravel;
	}
}
