package com.sleepwell.auth.utils;

import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.sleepwell.auth.dto.Principle;
import com.sleepwell.common.error.exception.ErrorCode;
import com.sleepwell.common.error.exception.UnAuthorizedException;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AuthUserResolver implements HandlerMethodArgumentResolver {

	private final AuthenticationStorage authenticationStorage;

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.hasParameterAnnotation(AuthUser.class)
			&& parameter.getParameterType().equals(Principle.class);
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
		NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
		return authenticationStorage.get()
			.orElseThrow(() -> new UnAuthorizedException(ErrorCode.NOT_LOGIN_REQUEST));
	}
}
