package com.achilles.wild.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

//@SpringBootApplication
@EnableAutoConfiguration
@Configuration
@ComponentScan(basePackages = {"com.achilles.wild.server"})
//@ImportResource("classpath:application.xml")
public class SpringbootApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootApplication.class, args);
	}

}
