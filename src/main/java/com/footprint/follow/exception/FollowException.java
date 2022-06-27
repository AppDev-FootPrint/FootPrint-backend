package com.footprint.follow.exception;

import com.footprint.common.exception.BaseException;
import com.footprint.common.exception.BaseExceptionType;

public class FollowException extends BaseException {
	private final FollowExceptionType followExceptionType;

	public FollowException(FollowExceptionType followExceptionType) {
		this.followExceptionType = followExceptionType;
	}

	@Override
	public BaseExceptionType getExceptionType() {
		return this.followExceptionType;
	}
}
