package com.sleepwell.common.error.dto;

import com.sleepwell.common.error.exception.BaseException;
import com.sleepwell.common.error.exception.ErrorCode;

public record ExceptionResponse(String errorMessage) {

	public static ExceptionResponse from(BaseException e) {
		return new ExceptionResponse(e.getErrorCode().getErrorMessage());
	}

	public static ExceptionResponse from(ErrorCode errorCode) {
		return new ExceptionResponse(errorCode.getErrorMessage());
	}
}
