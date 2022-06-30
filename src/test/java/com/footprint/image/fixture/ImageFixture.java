package com.footprint.image.fixture;

import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;

import org.springframework.test.util.ReflectionTestUtils;

import com.footprint.image.controller.dto.CreateImageRequest;
import com.footprint.image.controller.dto.ImageInfoResponse;
import com.footprint.image.domain.Image;
import com.footprint.image.service.dto.ImageDto;
import com.footprint.image.service.dto.ImageSaveDto;

/**
 * Created by ShinD on 2022/06/30.
 */
public class ImageFixture {

	public static final Long ID = 1L;
	public static final String IMAGE_PATH = "https://ttl-blog.tistory.com/";

	public static CreateImageRequest createImageRequest() {
		return new CreateImageRequest(IMAGE_PATH);
	}

	public static ImageSaveDto imageSaveDto() {
		return new ImageSaveDto(IMAGE_PATH);
	}

	public static Image imageHasId() {
		Image image = new Image(IMAGE_PATH);
		ReflectionTestUtils.setField(image, "id", ID);
		return image;
	}

	public static ImageDto imageDto() {
		return new ImageDto(IMAGE_PATH);
	}

	public static ImageInfoResponse imageInfoResponse() {
		return new ImageInfoResponse(IMAGE_PATH);
	}

	public static List<ImageSaveDto> imageSaveDtoList(int size) {
		return IntStream.rangeClosed(1, size).mapToObj(i -> new ImageSaveDto(IMAGE_PATH + UUID.randomUUID().toString())).toList();
	}

	public static List<CreateImageRequest> createImageRequestList(int size) {
		return IntStream.rangeClosed(1, size).mapToObj(i -> new CreateImageRequest(IMAGE_PATH + i)).toList();
	}

	public static List<Image> imageList(int size) {
		return IntStream.rangeClosed(1, size).mapToObj(i -> new Image(IMAGE_PATH + i)).toList();
	}

	public static List<ImageInfoResponse> imageInfoResponseList(int size) {
		return IntStream.rangeClosed(1, size).mapToObj(i -> new ImageInfoResponse(IMAGE_PATH + i)).toList();
	}

	public static List<ImageDto> imageDtoList(int size) {
		return IntStream.rangeClosed(1, size).mapToObj(i -> new ImageDto(IMAGE_PATH + i)).toList();
	}
}

