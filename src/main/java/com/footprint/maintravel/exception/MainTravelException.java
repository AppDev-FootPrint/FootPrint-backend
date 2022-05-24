package com.footprint.maintravel.exception;

import com.footprint.common.exception.BaseException;
import com.footprint.common.exception.BaseExceptionType;

public class MainTravelException extends BaseException {
	private final MainTravelExceptionType mainTravelExceptionType;

	public MainTravelException(MainTravelExceptionType mainTravelExceptionType) {
		this.mainTravelExceptionType = mainTravelExceptionType;
	}

	@Override
	public BaseExceptionType getExceptionType() {
		return mainTravelExceptionType;
	}
}
