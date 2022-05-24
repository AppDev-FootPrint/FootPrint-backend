package com.footprint.detailtravel.service.dto;

import com.footprint.image.domain.Image;

/**
 * Created by ShinD on 2022/05/25.
 */
public record ImageDto(String path) {
	//TODO 해당 DTO 의 위치가 애매함

	public static ImageDto from(Image image) {
		return new ImageDto(image.getPath());
	}
}
