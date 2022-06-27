package com.footprint.detailtravel.controller.dto.request;

import com.footprint.detailtravel.service.dto.create.PriceSaveDto;

/**
 * Created by ShinD on 2022/06/23.
 */
public record CreatePriceRequest(String item, int price){
	public PriceSaveDto toServiceDto() {
		return new PriceSaveDto(item(), price());
	}

}
