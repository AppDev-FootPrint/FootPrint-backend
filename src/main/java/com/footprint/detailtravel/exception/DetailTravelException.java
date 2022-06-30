package com.footprint.detailtravel.exception;

import com.footprint.common.exception.BaseException;
import com.footprint.common.exception.BaseExceptionType;

public class DetailTravelException extends BaseException {
	private final DetailTravelExceptionType detailTravelExceptionType;

	public DetailTravelException(DetailTravelExceptionType detailTravelExceptionType) {
		this.detailTravelExceptionType = detailTravelExceptionType;
	}

	@Override
	public BaseExceptionType getExceptionType() {
		return detailTravelExceptionType;
	}
}
