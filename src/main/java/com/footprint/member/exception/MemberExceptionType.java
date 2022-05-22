package com.footprint.member.exception;

import org.springframework.http.HttpStatus;

import com.footprint.common.exception.BaseExceptionType;

/**
 * Created by ShinD on 2022/05/14.
 */
public enum MemberExceptionType implements BaseExceptionType {

	NOT_FOUND(100, HttpStatus.NOT_FOUND, "회원이 존재하지 않습니다."),
	DUPLICATE_USERNAME(101, HttpStatus.CONFLICT,"이미 존재하는 아이디입니다.");



	private final int errorCode;
	private final HttpStatus httpStatus;
	private final String errorMessage;

	MemberExceptionType(int errorCode, HttpStatus httpStatus, String errorMessage) {
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
