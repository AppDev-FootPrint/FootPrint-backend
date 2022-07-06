package com.footprint.price.service.dto;

import com.footprint.price.domain.Price;

/**
 * Created by ShinD on 2022/06/23.
 */
public record PriceSaveDto(String item, int priceInfo) {
	public Price toEntity() {
		return new Price(item(), priceInfo());
	}
}
