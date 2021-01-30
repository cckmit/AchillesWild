package com.achilles.wild.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

@SpringBootApplication
//@EnableAutoConfiguration
//@Configuration
//@ComponentScan(basePackages = {"com.achilles.wild.server"})
//@ImportResource("classpath:applicationContext.xml")
//@MapperScan(basePackages = {"com.achilles.wild.server.dao.account"})
public class SpringbootApplication {

	public static void main(String[] args) {

		ApplicationContext applicationContext = SpringApplication.run(SpringbootApplication.class, args);
		DataSource dataSource = applicationContext.getBean(DataSource.class);

		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~datasource is : " + dataSource+"~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");

		try {
			Connection connection = dataSource.getConnection();
			ResultSet rs = connection.createStatement().executeQuery("SELECT 1");
			if (rs.first()) {
				System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~Connect database SUCCESS ! ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
			} else {
				System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~Connect database FAIL ! ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
