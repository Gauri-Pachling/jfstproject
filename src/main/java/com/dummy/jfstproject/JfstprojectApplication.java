package com.dummy.jfstproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class JfstprojectApplication {

	public static void main(String[] args) {
		SpringApplication.run(JfstprojectApplication.class, args);
	}

}
