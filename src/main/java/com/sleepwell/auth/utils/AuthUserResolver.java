package com.sleepwell.auth.utils;

import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.sleepwell.auth.dto.Principle;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AuthUserResolver implements HandlerMethodArgumentResolver {

	private static final String ERROR_MESSAGE_NOT_LOGIN = "로그인 정보가 존재하지 않습니다.";

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
			.orElseThrow(() -> new RuntimeException(ERROR_MESSAGE_NOT_LOGIN));
	}
}
