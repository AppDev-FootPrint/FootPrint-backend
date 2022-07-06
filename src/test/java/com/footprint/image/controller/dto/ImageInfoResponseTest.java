package com.footprint.image.controller.dto;

import static com.footprint.image.fixture.ImageFixture.*;
import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.footprint.image.service.dto.ImageDto;

/**
 * Created by ShinD on 2022/06/30.
 */
class ImageInfoResponseTest {
	
	private final ObjectMapper objectMapper = new ObjectMapper();

	private static final String EXPECT = """
  	{
			"path" : "%s"
  	}
		""".formatted(IMAGE_PATH);


	@Test
	@DisplayName("ImageInfoResponse -> json 변환 테스트")
	public void imageInfoResponseToJsonTest() throws Exception {
		//given
		ImageInfoResponse imageInfoResponse = imageInfoResponse();

		//when
		String content = objectMapper.writeValueAsString(imageInfoResponse);

		//then
		assertThat(content).isEqualTo(EXPECT.replaceAll(" ", "").replaceAll("\n","").replaceAll("\t",""));

	}



	@Test
	@DisplayName("ImageDto -> ImageInfoResponse 변환 테스트")
	public void imageDtoToImageInfoResponseTest() throws Exception {
		//given
		ImageDto imageDto = imageDto();

		//when
		ImageInfoResponse imageInfoResponse = ImageInfoResponse.from(imageDto);

		//then
		assertThat(imageInfoResponse.path()).isEqualTo(imageDto.path());
	}
}