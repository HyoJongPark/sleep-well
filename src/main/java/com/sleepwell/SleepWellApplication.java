package com.sleepwell;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;

@EnableRetry
@SpringBootApplication
public class SleepWellApplication {

	public static void main(String[] args) {
		SpringApplication.run(SleepWellApplication.class, args);
	}

}
