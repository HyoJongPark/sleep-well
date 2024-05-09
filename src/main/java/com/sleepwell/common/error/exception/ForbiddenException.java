package com.sleepwell.common.error.exception;

public class ForbiddenException extends BaseException {
	public ForbiddenException(ErrorCode errorCode) {
		super(errorCode);
	}
}
