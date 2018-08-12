package com.zl.sneakerserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication()
@ComponentScan(basePackages = {"com.zl.sneakerentity","com.zl.sneakerserver"})
public class SneakerServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SneakerServerApplication.class, args);
    }
}
