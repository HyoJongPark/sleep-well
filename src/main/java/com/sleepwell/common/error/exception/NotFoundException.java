package com.sleepwell.common.error.exception;

public class NotFoundException extends BaseException {
	public NotFoundException(ErrorCode errorCode) {
		super(errorCode);
	}
}
