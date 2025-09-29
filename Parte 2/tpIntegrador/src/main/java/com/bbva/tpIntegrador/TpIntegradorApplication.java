package com.bbva.tpIntegrador;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class TpIntegradorApplication {

	public static void main(String[] args) {
		SpringApplication.run(TpIntegradorApplication.class, args);
	}

}
