package com.footprint.image.service.dto;

import static com.footprint.image.fixture.ImageFixture.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.footprint.image.domain.Image;

/**
 * Created by ShinD on 2022/06/30.
 */
class ImageDtoTest {

	@Test
	@DisplayName("Image -> ImageSaveDto 변환 테스트")
	public void imageToImageSaveDtoTest() throws Exception {
		//given
		Image image = imageHasId();

		//when
		ImageDto imageDto = ImageDto.from(image);

		//then
		Assertions.assertThat(imageDto.path()).isEqualTo(image.getPath());
	}
}