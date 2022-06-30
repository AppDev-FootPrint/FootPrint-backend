package com.footprint.scrap.exception;

import com.footprint.common.exception.BaseException;
import com.footprint.common.exception.BaseExceptionType;

public class ScrapException extends BaseException {
	private final ScrapExceptionType scrapExceptionType;

	public ScrapException(ScrapExceptionType scrapExceptionType) {
		this.scrapExceptionType = scrapExceptionType;
	}

	@Override
	public BaseExceptionType getExceptionType() {
		return scrapExceptionType;
	}
}
