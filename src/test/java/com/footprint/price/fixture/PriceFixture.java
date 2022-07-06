package com.footprint.price.fixture;

import java.util.List;
import java.util.stream.IntStream;

import org.springframework.test.util.ReflectionTestUtils;

import com.footprint.price.controller.dto.CreatePriceRequest;
import com.footprint.price.controller.dto.PriceInfoResponse;
import com.footprint.price.domain.Price;
import com.footprint.price.service.dto.PriceDto;
import com.footprint.price.service.dto.PriceSaveDto;

/**
 * Created by ShinD on 2022/06/30.
 */
public class PriceFixture {

	public static final Long ID = 1L;
	public static final String ITEM = "item";
	public static final int PRICE = 10000;

	public static CreatePriceRequest createPriceRequest() {
		return new CreatePriceRequest(ITEM, PRICE);
	}

	public static PriceSaveDto priceSaveDto() {
		return new PriceSaveDto(ITEM, PRICE);
	}

	public static Price priceHasId() {
		Price price = new Price(ITEM, PRICE);
		ReflectionTestUtils.setField(price, "id", ID);
		return price;
	}

	public static PriceDto priceDto() {
		return new PriceDto(ITEM, PRICE);
	}

	public static PriceInfoResponse priceInfoResponse() {
		return new PriceInfoResponse(ITEM, PRICE);
	}

	public static List<PriceSaveDto> priceSaveDtoList(int size) {
		return IntStream.rangeClosed(1, size).mapToObj(i -> new PriceSaveDto(ITEM + i, PRICE * i)).toList();
	}

	public static List<CreatePriceRequest> createPriceRequestList(int size) {
		return IntStream.rangeClosed(1, size).mapToObj(i -> new CreatePriceRequest(ITEM + i, PRICE * i)).toList();
	}

	public static List<Price> priceList(int size) {
		return IntStream.rangeClosed(1, size).mapToObj(i -> new Price(ITEM + i, PRICE * i)).toList();
	}

	public static List<PriceInfoResponse> priceInfoResponseList(int size) {
		return IntStream.rangeClosed(1, size).mapToObj(i -> new PriceInfoResponse(ITEM + i, PRICE * i)).toList();
	}

	public static List<PriceDto> priceDtoList(int size) {
		return IntStream.rangeClosed(1, size).mapToObj(i -> new PriceDto(ITEM + i, PRICE * i)).toList();
	}
}

