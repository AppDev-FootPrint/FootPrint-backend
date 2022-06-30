package com.footprint.image.service.dto;

import static com.footprint.image.fixture.ImageFixture.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.footprint.image.domain.Image;

/**
 * Created by ShinD on 2022/06/30.
 */
class ImageSaveDtoTest {

	@Test
	@DisplayName("ImageSaveDto -> Image 변환 테스트")
	public void imageSaveDtoToImageTest() throws Exception {
		//given
		ImageSaveDto imageSaveDto = imageSaveDto();

		//when
		Image image = imageSaveDto.toEntity();

		//then
		Assertions.assertThat(image.getId()).isNull();
		Assertions.assertThat(image.getDetailTravel()).isNull();
		Assertions.assertThat(image.getPath()).isEqualTo(imageSaveDto.path());
	}
}