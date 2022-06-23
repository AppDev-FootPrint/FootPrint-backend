package com.footprint.maintravel.controller.dto.request.create;

import com.footprint.maintravel.service.dto.save.ImageSaveDto;

/**
 * Created by ShinD on 2022/06/23.
 */
public record CreateImageRequest() {
	public ImageSaveDto toServiceDto() {
		return null;
	}
}
