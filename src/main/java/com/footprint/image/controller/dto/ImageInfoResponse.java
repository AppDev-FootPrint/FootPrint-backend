package com.footprint.image.controller.dto;

import com.footprint.image.service.dto.ImageDto;

/**
 * Created by ShinD on 2022/06/27.
 */
public record ImageInfoResponse(String path) {
	public static ImageInfoResponse from(ImageDto imageDto) {
		return new ImageInfoResponse(imageDto.path());
	}
}
