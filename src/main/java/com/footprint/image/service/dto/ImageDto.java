package com.footprint.image.service.dto;

import com.footprint.image.domain.Image;

/**
 * Created by ShinD on 2022/05/25.
 */
public record ImageDto(String path) {

	public static ImageDto from(Image image) {
		return new ImageDto(image.getPath());
	}
}
