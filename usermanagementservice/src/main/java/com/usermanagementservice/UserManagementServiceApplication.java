package com.usermanagementservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.reactive.config.EnableWebFlux;


/**
 * The class UsermanagementserviceApplication
 * 
 * @author harsh.jain
 *
 */
@SpringBootApplication
@EnableWebFlux
public class UserManagementServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserManagementServiceApplication.class, args);
	}

}
