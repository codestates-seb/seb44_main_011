package com.seb44main011.petplaylist;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;


@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class PetplaylistApplication {

	public static void main(String[] args) {
		SpringApplication.run(PetplaylistApplication.class, args);
	}

}
