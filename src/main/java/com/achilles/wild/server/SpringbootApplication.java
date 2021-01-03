package com.achilles.wild.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

//@SpringBootApplication
@EnableAutoConfiguration
@Configuration
@ComponentScan(basePackages = {"com.achilles.wild.server"})
@ImportResource("classpath:applicationContext.xml")
public class SpringbootApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootApplication.class, args);
	}

}
