package com.achilles.wild.server.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value = "classpath:properties/test2.properties",encoding = "UTF-8")
public class ConfigProperties2 {

    @Value("${house:Stark}")
    private String house;

    public String getHouse() {
        return house;
    }

    public void setHouse(String house) {
        this.house = house;
    }
}
