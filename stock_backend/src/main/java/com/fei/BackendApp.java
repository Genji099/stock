package com.fei;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


//@EnableFeignClients(basePackages = "com.example.client")
//@MapperScan("pace.project.stcok_parent.stock_backend.src.main.java.com.fei.stock.mapper")
@SpringBootApplication
@MapperScan("com.fei.stock.mapper")
public class BackendApp {
    public static void main(String[] args) {
        SpringApplication.run(BackendApp.class, args);
    }
}
