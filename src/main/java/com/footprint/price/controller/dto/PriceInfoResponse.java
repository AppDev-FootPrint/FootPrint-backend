package com.footprint.price.controller.dto;

import com.footprint.price.service.dto.PriceDto;

/**
 * Created by ShinD on 2022/06/27.
 */
public record PriceInfoResponse(String item, int priceInfo) {

	public static PriceInfoResponse from(PriceDto priceDto) {
		return new PriceInfoResponse(priceDto.item(), priceDto.priceInfo());
	}
}
