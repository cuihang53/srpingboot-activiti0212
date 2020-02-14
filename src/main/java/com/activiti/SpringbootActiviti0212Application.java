package com.activiti;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@ServletComponentScan
@EnableScheduling
@EnableJpaRepositories("com.activiti.web.dao")
@EntityScan("com.activiti.entity")
//@scan
@EnableAutoConfiguration(exclude={
		org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class, 
		org.activiti.spring.boot.SecurityAutoConfiguration.class})
public class SpringbootActiviti0212Application {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootActiviti0212Application.class, args);
	}

}
