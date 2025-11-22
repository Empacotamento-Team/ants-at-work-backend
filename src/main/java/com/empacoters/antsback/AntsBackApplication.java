package com.empacoters.antsback;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class AntsBackApplication {

	public static void main(String[] args) {
		SpringApplication.run(AntsBackApplication.class, args);
	}

}
