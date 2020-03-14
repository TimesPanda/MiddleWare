package com.times.server;

import org.mybatis.spring.annotation.MapperScan;
        import org.springframework.boot.SpringApplication;
        import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "com.times.model")
public class MainApplication {


    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class,args);
    }
}
