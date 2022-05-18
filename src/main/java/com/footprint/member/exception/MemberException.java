package com.footprint.member.exception;

import com.footprint.common.exception.BaseException;
import com.footprint.common.exception.BaseExceptionType;

/**
 * Created by ShinD on 2022/05/14.
 */
public class MemberException extends BaseException {

	private final MemberExceptionType memberExceptionType;

	public MemberException(MemberExceptionType memberExceptionType) {
		this.memberExceptionType = memberExceptionType;
	}

	@Override
	public BaseExceptionType getExceptionType() {
		return this.memberExceptionType;
	}
}
