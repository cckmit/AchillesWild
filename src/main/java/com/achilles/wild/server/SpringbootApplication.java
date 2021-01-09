package com.achilles.wild.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@EnableAutoConfiguration
//@Configuration
//@ComponentScan(basePackages = {"com.achilles.wild.server"})
//@ImportResource("classpath:applicationContext.xml")
//@MapperScan(basePackages = {"com.achilles.wild.server.dao.account"})
public class SpringbootApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootApplication.class, args);
	}

}
