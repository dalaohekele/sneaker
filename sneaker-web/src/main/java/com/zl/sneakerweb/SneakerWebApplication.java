package com.zl.sneakerweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@ComponentScan(basePackages = {"com.zl.sneakerentity", "com.zl.sneakerserver",
        "com.zl.sneakerweb"})
@EnableSwagger2
public class SneakerWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(SneakerWebApplication.class, args);
    }
}
