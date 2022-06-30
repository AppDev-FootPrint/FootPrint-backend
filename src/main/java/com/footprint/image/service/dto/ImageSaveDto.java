package com.footprint.image.service.dto;

import com.footprint.image.domain.Image;

/**
 * Created by ShinD on 2022/06/23.
 */
public record ImageSaveDto(String path) {
	public Image toEntity() {
		return new Image(path());
	}
}
