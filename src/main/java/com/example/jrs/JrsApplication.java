package com.example.jrs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = "com.example.jrs.entity")
@EnableJpaRepositories(basePackages = "com.example.jrs.repo")
public class JrsApplication {

	public static void main(String[] args) {
		SpringApplication.run(JrsApplication.class, args);
	}

}
