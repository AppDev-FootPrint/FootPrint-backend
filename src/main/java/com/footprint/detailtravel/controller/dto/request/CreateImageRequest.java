package com.footprint.detailtravel.controller.dto.request;

import com.footprint.detailtravel.service.dto.create.ImageSaveDto;

/**
 * Created by ShinD on 2022/06/23.
 */
public record CreateImageRequest(String imagePath) {
	public ImageSaveDto toServiceDto() {
		return null;
	}
}
