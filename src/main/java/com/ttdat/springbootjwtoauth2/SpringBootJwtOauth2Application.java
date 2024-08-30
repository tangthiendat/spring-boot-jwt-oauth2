package com.ttdat.springbootjwtoauth2;

import com.ttdat.springbootjwtoauth2.config.RSAKeyRecord;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties(RSAKeyRecord.class)
@SpringBootApplication
public class SpringBootJwtOauth2Application {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootJwtOauth2Application.class, args);
    }

}
