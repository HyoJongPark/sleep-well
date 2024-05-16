package com.sleepwell.common.error.exception;

public enum ErrorCode {
	//400
	BAD_REQUEST("잘못된 요청입니다."),
	ROOM_NOT_FOUND("존재하지 않는 숙소입니다."),
	INVALID_COUPON_CODE("유효하지 않는 쿠폰 코드입니다."),
	COUPON_OUT_OF_STOCK("재고가 소진된 쿠폰입니다."),
	ALREADY_ISSUED_COUPON("이미 발급된 쿠폰입니다."),
	INVALID_RESERVATION_DATE("해당 일자에 예약이 불가합니다."),
	INVALID_NUMBER_OF_GUEST("예약 가능 인원을 초과했습니다."),
	USER_NOT_FOUND("존재하지 않는 사용자 입니다."),
	INVALID_GOGGLE_OAUTH2_REQUEST("유효하지 않은 구글 oauth 요청입니다. 로그인 정보를 확인해주세요."),
	INVALID_RESERVATION_GUEST("숙소 예약자 정보와 현재 사용자가 일치하지 않습니다."),
	NOT_EXIST_ISSUED_COUPON("발급된 쿠폰이 존재하지 않습니다."),
	INVALID_COUPON_ISSUER("예약자가 소유한 쿠폰이 아닙니다."),
	ALREADY_USED_ISSUED_COUPON("이미 사용된 쿠폰입니다."),

	//401
	TOKEN_NOT_EXIST("인증 토큰이 존재하지 않습니다."),
	INVALID_JWT_SIGNATURE("잘못된 JWT 서명입니다."),
	EXPIRED_JWT("만료된 JWT 토큰입니다."),
	NOT_SUPPORT_JWT("지원되지 않는 JWT 토큰입니다."),
	INVALID_JWT_FORMAT("잘못된 JWT 토큰 형식입니다."),
	NOT_LOGIN_REQUEST("로그인 정보가 존재하지 않습니다."),

	//403
	FORBIDDEN("접근 권한이 올바르지 않습니다."),

	//404
	NOT_EXIST_RESERVATION("존재하지 않는 예약 정보입니다."),

	//500
	INTERNAL_SERVER_ERROR("내부 서버에 문제가 발생했습니다."),
	ALREADY_EXIST_SOCIAL_TYPE("이미 존재하는 SocialType 입니다."),
	NOT_EXIST_SOCIAL_TYPE("존재하지 않은 소셜 타입입니다."),
	;

	private final String errorMessage;

	ErrorCode(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getErrorMessage() {
		return errorMessage;
	}
}
