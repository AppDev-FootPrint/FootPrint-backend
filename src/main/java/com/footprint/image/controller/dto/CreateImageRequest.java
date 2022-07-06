package com.footprint.image.controller.dto;

import com.footprint.image.service.dto.ImageSaveDto;

/**
 * Created by ShinD on 2022/06/27.
 */
public record CreateImageRequest(String path) {
	public ImageSaveDto toServiceDto() {
		return new ImageSaveDto(path());
	}
}
