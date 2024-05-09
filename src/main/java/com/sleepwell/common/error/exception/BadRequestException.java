package com.sleepwell.common.error.exception;

public class BadRequestException extends BaseException {
	public BadRequestException(ErrorCode errorCode) {
		super(errorCode);
	}
}
