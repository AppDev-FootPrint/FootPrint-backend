package com.footprint.price.controller.dto;

import com.footprint.price.service.dto.PriceSaveDto;

/**
 * Created by ShinD on 2022/06/23.
 */
public record CreatePriceRequest(String item, int priceInfo){
	public PriceSaveDto toServiceDto() {
		return new PriceSaveDto(item(), priceInfo());
	}

}
