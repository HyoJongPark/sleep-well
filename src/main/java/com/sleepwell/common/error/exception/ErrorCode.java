package com.sleepwell.common.error.exception;

public enum ErrorCode {
	//400
	BAD_REQUEST("잘못된 요청입니다."),

	//500
	INTERNAL_SERVER_ERROR("내부 서버에 문제가 발생했습니다."),
	;

	private final String errorMessage;

	ErrorCode(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getErrorMessage() {
		return errorMessage;
	}
}
