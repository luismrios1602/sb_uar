package com.luzinho.sbuar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@ConfigurationPropertiesScan
public class SbuarApplication {

	public static void main(String[] args) {
		SpringApplication.run(SbuarApplication.class, args);
	}

    @Bean
    @ConfigurationProperties(prefix = "user-bean")
    UserBean createUserBean(){
        return new UserBean();
    }

}
