package com.bootx;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * @author blackboy1987
 */
@SpringBootApplication
@EnableJpaAuditing
public class BisheApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(BisheApiApplication.class, args);
    }

}
