package com.footprint.detailtravel.controller.dto.response;

import com.footprint.detailtravel.service.dto.info.ImageDto;

/**
 * Created by ShinD on 2022/06/27.
 */
public record ImageInfoResponse(String path) {
	public static ImageInfoResponse from(ImageDto imageDto) {
		return new ImageInfoResponse(imageDto.path());
	}
}
