package com.ian.helpdesk.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.ian.helpdesk.services.DBService;

@Configuration
@Profile("test")
public class TestConfig {

    @Autowired
    DBService dbService;

    @Bean
    public void instanciaDB() {
        this.dbService.instanciaDB();
    }
}
