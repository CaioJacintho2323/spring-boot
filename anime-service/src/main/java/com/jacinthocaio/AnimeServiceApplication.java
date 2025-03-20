package com.jacinthocaio;

import com.jacinthocaio.config.ConnectionConfigurationProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication(scanBasePackages = "com.jacinthocaio")
@EnableConfigurationProperties(value = ConnectionConfigurationProperties.class)
//@ConfigurationPropertiesScan
public class AnimeServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AnimeServiceApplication.class, args);
    }

}
