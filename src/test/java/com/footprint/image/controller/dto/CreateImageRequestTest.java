package com.footprint.image.controller.dto;

import static com.footprint.image.fixture.ImageFixture.*;
import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.footprint.image.service.dto.ImageSaveDto;

/**
 * Created by ShinD on 2022/06/30.
 */
class CreateImageRequestTest {
	private final ObjectMapper objectMapper = new ObjectMapper();
	private static final String REQUEST_BODY = """
		{
			"path" : "%s"
		}
		""".formatted(IMAGE_PATH);


	@Test
	@DisplayName("Json -> CreateImageRequest 변환 테스트")
	void jsonToCreateMainTravelRequestTest() throws Exception {
		//given, when
		CreateImageRequest createImageRequest = objectMapper.readValue(REQUEST_BODY, CreateImageRequest.class);

		//then
		assertThat(createImageRequest.path()).isEqualTo(IMAGE_PATH);


	}


	@Test
	@DisplayName("CreateImageRequest -> ImageSaveDto 변환 테스트")
	void createMainTravelRequestToMainTravelSaveDtoTest() throws Exception {
		//given
		CreateImageRequest createImageRequest = createImageRequest();

		//when
		ImageSaveDto imageSaveDto = createImageRequest.toServiceDto();

		//then
		assertThat(imageSaveDto.path()).isEqualTo(createImageRequest.path());

	}
}