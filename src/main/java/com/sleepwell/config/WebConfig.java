package com.sleepwell.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.sleepwell.auth.utils.AuthUserResolver;
import com.sleepwell.auth.utils.AuthenticationStorage;
import com.sleepwell.auth.utils.JwtExtractor;
import com.sleepwell.auth.utils.JwtInterceptor;
import com.sleepwell.user.domain.Role;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

	private final JwtExtractor jwtExtractor;
	private final AuthUserResolver authUserResolver;
	private final AuthenticationStorage authenticationStorage;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(normalJwtInterceptor())
			.excludePathPatterns("/auth/**", "/error", "/*.html")
			.addPathPatterns("/rooms/**", "/coupon/**", "/reservation/**", "/user/**");

		registry.addInterceptor(adminJwtInterceptor())
			.addPathPatterns("/admin/**");
	}

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
		resolvers.add(authUserResolver);
	}

	@Bean
	public JwtInterceptor normalJwtInterceptor() {
		return new JwtInterceptor(Role.NORMAL, jwtExtractor, authenticationStorage);
	}

	@Bean
	public JwtInterceptor adminJwtInterceptor() {
		return new JwtInterceptor(Role.ADMIN, jwtExtractor, authenticationStorage);
	}
}
