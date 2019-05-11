package com.mtemnohud.dbuilder;

import com.mtemnohud.dbuilder.swagger.EnableSwagger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableSwagger
@EnableAsync
@SpringBootApplication
@ComponentScan(basePackages = {"com.mtemnohud.dbuilder"})
@EnableConfigurationProperties
@EnableJpaRepositories(basePackages = {"com.mtemnohud.dbuilder.repository"})
@EntityScan(value = {"com.mtemnohud.dbuilder.model"})
@EnableScheduling
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
