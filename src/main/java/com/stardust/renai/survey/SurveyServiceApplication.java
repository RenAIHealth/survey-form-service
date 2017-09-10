package com.stardust.renai.survey;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = {"com.stardust.easyassess.assessment.dao.repositories"})
public class SurveyServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SurveyServiceApplication.class, args);
	}
}
