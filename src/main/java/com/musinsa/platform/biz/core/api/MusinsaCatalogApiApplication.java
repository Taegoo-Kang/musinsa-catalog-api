package com.musinsa.platform.biz.core.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = "com.musinsa.platform.biz.core")
@SpringBootApplication
public class MusinsaCatalogApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(MusinsaCatalogApiApplication.class, args);
	}

}
