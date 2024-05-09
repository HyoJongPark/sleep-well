package com.sleepwell.common.error.exception;

public class UnAuthorizedException extends BaseException {
	public UnAuthorizedException(ErrorCode errorCode) {
		super(errorCode);
	}
}
