package com.capston.mainserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication()
public class MainserverApplication {

	public static void main(String[] args) {
		SpringApplication.run(MainserverApplication.class, args);
	}

}
