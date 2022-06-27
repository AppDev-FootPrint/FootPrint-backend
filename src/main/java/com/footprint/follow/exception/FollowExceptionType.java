package com.footprint.follow.exception;

import org.springframework.http.HttpStatus;

import com.footprint.common.exception.BaseExceptionType;

public enum FollowExceptionType implements BaseExceptionType {
	NOT_FOUND(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND, "팔로우가 존재하지 않습니다."),
	CONFLICT(HttpStatus.CONFLICT.value(), HttpStatus.CONFLICT, "중복된 팔로우가 존재합니다.");

	private final int errorCode;
	private final HttpStatus httpStatus;
	private final String errorMessage;

	FollowExceptionType(int errorCode, HttpStatus httpStatus, String errorMessage) {
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
