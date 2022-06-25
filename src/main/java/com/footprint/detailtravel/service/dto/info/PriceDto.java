package com.footprint.detailtravel.service.dto.info;

import com.footprint.price.domain.Price;

/**
 * Created by ShinD on 2022/05/24.
 */
public record PriceDto(String item, int priceInfo) {

	public static PriceDto from(Price price) {
		return new PriceDto(price.getItem(), price.getPriceInfo());
	}

}
