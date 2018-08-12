package com.zl.sneakerentity;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.zl.sneakerentity.dao")
public class SneakerEntityApplication {

    public static void main(String[] args) {
        SpringApplication.run(SneakerEntityApplication.class, args);
    }
}
