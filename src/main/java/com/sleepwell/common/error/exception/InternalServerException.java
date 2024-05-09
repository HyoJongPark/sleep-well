package com.sleepwell.common.error.exception;

public class InternalServerException extends BaseException {
	public InternalServerException(ErrorCode errorCode) {
		super(errorCode);
	}
}
