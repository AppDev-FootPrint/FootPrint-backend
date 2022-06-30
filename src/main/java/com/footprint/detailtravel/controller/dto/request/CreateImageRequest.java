package com.footprint.detailtravel.controller.dto.request;

import com.footprint.detailtravel.service.dto.create.ImageSaveDto;

/**
 * Created by ShinD on 2022/06/27.
 */
public record CreateImageRequest(String path) {
	public ImageSaveDto toServiceDto() {
		return new ImageSaveDto(path());
	}
}
