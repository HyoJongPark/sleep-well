package com.sleepwell.auth.utils;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.sleepwell.auth.dto.Principle;

@Component
public class AuthenticationStorage {

	private final ThreadLocal<Principle> storage = new ThreadLocal<>();

	public void set(Principle principle) {
		storage.set(principle);
	}

	public Optional<Principle> get() {
		return Optional.ofNullable(storage.get());
	}

	public void remove() {
		storage.remove();
	}
}
