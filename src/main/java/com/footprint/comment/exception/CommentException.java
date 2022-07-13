package com.footprint.comment.exception;

import com.footprint.common.exception.BaseException;
import com.footprint.common.exception.BaseExceptionType;

public class CommentException extends BaseException {
	private final CommentExceptionType commentExceptionType;

	public CommentException(CommentExceptionType commentExceptionType) {
		this.commentExceptionType = commentExceptionType;
	}

	@Override
	public BaseExceptionType getExceptionType() {
		return this.commentExceptionType;
	}
}
