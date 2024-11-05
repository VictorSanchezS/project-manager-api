package com.victorsanchez.projectmanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ProjectManagerApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjectManagerApiApplication.class, args);
	}

}
