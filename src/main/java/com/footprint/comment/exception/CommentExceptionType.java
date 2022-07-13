package com.footprint.comment.exception;

import org.springframework.http.HttpStatus;

import com.footprint.common.exception.BaseExceptionType;

public enum CommentExceptionType implements BaseExceptionType {
	NOT_FOUND(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND, "해당 댓글이 존재하지 않습니다."),
	NO_AUTHORITY(HttpStatus.FORBIDDEN.value(), HttpStatus.NOT_FOUND, "해당 댓글에 대해 작업할 권한이 없습니다.");

	private final int errorCode;
	private final HttpStatus httpStatus;
	private final String errorMessage;

	CommentExceptionType(int errorCode, HttpStatus httpStatus, String errorMessage) {
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
