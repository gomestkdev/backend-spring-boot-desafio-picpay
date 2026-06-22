package com.github.gomestkd.backend_desafio_picpay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jdbc.repository.config.EnableJdbcAuditing;

@EnableJdbcAuditing
@SpringBootApplication
public class BackendDesafioPicpayApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendDesafioPicpayApplication.class, args);
	}

}
