package com.ranjith.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication()
public class UserServiceApplication {

	public static void main(String[] args) {
		System.out.println("JVM default timezone: " + java.util.TimeZone.getDefault().getID());

		SpringApplication.run(UserServiceApplication.class, args);
		System.out.println("JVM default timezone: " + java.util.TimeZone.getDefault().getID());

	}

}
