package com.anqili.application;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@MapperScan("com.anqili.application.dao")
public class CourseSchedulingApplication {

	public static void main(String[] args) {
		SpringApplication.run(CourseSchedulingApplication.class, args);
	}

}
