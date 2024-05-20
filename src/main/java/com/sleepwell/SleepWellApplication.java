package com.sleepwell;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class SleepWellApplication {

	public static void main(String[] args) {
		SpringApplication.run(SleepWellApplication.class, args);
	}
}
