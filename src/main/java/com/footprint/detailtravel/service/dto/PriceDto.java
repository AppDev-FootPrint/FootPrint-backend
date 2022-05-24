package com.footprint.detailtravel.service.dto;

import com.footprint.price.domain.Price;

/**
 * Created by ShinD on 2022/05/24.
 */
public record PriceDto(String item, int priceInfo) {
	//TODO 해당 DTO 의 위치가 애매함

	public static PriceDto from(Price price) {
		return new PriceDto(price.getItem(), price.getPriceInfo());
	}

}
