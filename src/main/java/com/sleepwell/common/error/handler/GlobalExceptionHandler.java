package com.sleepwell.common.error.handler;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.sleepwell.auth.dto.Principle;
import com.sleepwell.auth.utils.AuthenticationStorage;
import com.sleepwell.common.error.dto.ExceptionResponse;
import com.sleepwell.common.error.exception.BadRequestException;
import com.sleepwell.common.error.exception.BaseException;
import com.sleepwell.common.error.exception.ErrorCode;
import com.sleepwell.common.error.exception.ForbiddenException;
import com.sleepwell.common.error.exception.NotFoundException;
import com.sleepwell.common.error.exception.UnAuthorizedException;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

	private static final String LOG_FORMAT_INFO = "\n[🟢INFO] - ({} {})\n(id: {}, role: {})";
	private static final String LOG_FORMAT_WARN = "\n[🟠WARN] - ({} {})\n(id: {}, role: {})";
	private static final String LOG_FORMAT_ERROR = "\n[🔴ERROR] - ({} {})\n(id: {}, role: {})";
	private static final String ANONYMOUS_LOG_FORMAT_INFO = "\n[🟢INFO] - ({} {})\n(anonymous user request)\n";
	private static final String ANONYMOUS_LOG_FORMAT_WARN = "\n[🟠WARN] - ({} {})\n(anonymous user request)\n";
	private static final String ANONYMOUS_LOG_FORMAT_ERROR = "\n[🔴ERROR] - ({} {})\n(anonymous user request)\n";

	private final AuthenticationStorage authenticationStorage;

	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<ExceptionResponse> handler(BadRequestException e, HttpServletRequest request) {
		logInfo(e, request);

		return ResponseEntity
			.badRequest()
			.body(ExceptionResponse.from(e));
	}

	@ExceptionHandler(ForbiddenException.class)
	public ResponseEntity<ExceptionResponse> handler(ForbiddenException e, HttpServletRequest request) {
		logInfo(e, request);

		return ResponseEntity
			.status(HttpStatus.FORBIDDEN)
			.body(ExceptionResponse.from(e));
	}

	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<ExceptionResponse> handler(NotFoundException e, HttpServletRequest request) {
		logInfo(e, request);

		return ResponseEntity
			.status(HttpStatus.NOT_FOUND)
			.body(ExceptionResponse.from(e));
	}

	@ExceptionHandler(UnAuthorizedException.class)
	public ResponseEntity<ExceptionResponse> handler(UnAuthorizedException e, HttpServletRequest request) {
		logInfo(e, request);

		return ResponseEntity
			.status(HttpStatus.UNAUTHORIZED)
			.body(ExceptionResponse.from(e));
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ExceptionResponse> handler(Exception e, HttpServletRequest request) {
		logError(e, request);

		return ResponseEntity
			.internalServerError()
			.body(ExceptionResponse.from(ErrorCode.INTERNAL_SERVER_ERROR));
	}

	private void logInfo(BaseException e, HttpServletRequest request) {
		Optional<Principle> principle = authenticationStorage.get();

		if (principle.isEmpty()) {
			log.info(ANONYMOUS_LOG_FORMAT_INFO, request.getMethod(), request.getRequestURI(), e);
			return;
		}

		log.info(LOG_FORMAT_INFO, request.getMethod(), request.getRequestURI(), principle.get().id(),
			principle.get().role(), e);
	}

	private void logWarn(BaseException e, HttpServletRequest request) {
		Optional<Principle> principle = authenticationStorage.get();

		if (principle.isEmpty()) {
			log.warn(ANONYMOUS_LOG_FORMAT_WARN, request.getMethod(), request.getRequestURI(), e);
			return;
		}

		log.warn(LOG_FORMAT_WARN, request.getMethod(), request.getRequestURI(), principle.get().id(),
			principle.get().role(), e);
	}

	private void logError(Exception e, HttpServletRequest request) {
		Optional<Principle> principle = authenticationStorage.get();

		if (principle.isEmpty()) {
			log.error(ANONYMOUS_LOG_FORMAT_ERROR, request.getMethod(), request.getRequestURI(), e);
			return;
		}

		log.error(LOG_FORMAT_ERROR, request.getMethod(), request.getRequestURI(), principle.get().id(),
			principle.get().role(), e);
	}
}
