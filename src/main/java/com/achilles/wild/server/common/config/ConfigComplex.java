package com.achilles.wild.server.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigComplex {

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DBConfig dbConfig(){
        return new DBConfig();
    }
}
