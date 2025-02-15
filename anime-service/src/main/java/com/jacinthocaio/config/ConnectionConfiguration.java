package com.jacinthocaio.config;

import com.external.dependency.Connection;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConnectionConfiguration  {

    @Bean
    public Connection connection(){
        return new Connection("localhost", "devdojo", "caio");
    }
}
