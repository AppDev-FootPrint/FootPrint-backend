package com.footprint.scrap.exception;

import org.springframework.http.HttpStatus;

import com.footprint.common.exception.BaseExceptionType;

public enum ScrapExceptionType implements BaseExceptionType {
	NOT_FOUND(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND, "해당 스크랩을 찾을 수 없습니다."),
	CONFLICT(HttpStatus.CONFLICT.value(), HttpStatus.CONFLICT, "해당 스크랩이 중복 되었습니다.");

	private final int errorCode;
	private final HttpStatus httpStatus;
	private final String errorMessage;

	ScrapExceptionType(int errorCode, HttpStatus httpStatus, String errorMessage) {
		this.errorCode = errorCode;
		this.httpStatus = httpStatus;
		this.errorMessage = errorMessage;
	}

	@Override
	public int getErrorCode() {
		return this.errorCode;
	}

	@Override
	public HttpStatus getHttpStatus() {
		return this.httpStatus;
	}

	@Override
	public String getErrorMessage() {
		return this.errorMessage;
	}
}
