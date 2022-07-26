package com.application.godzilla;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude={SecurityAutoConfiguration.class})
public class GodzillaApplication {

	public static void main(String[] args) {
		SpringApplication.run(GodzillaApplication.class, args);
	}

}
