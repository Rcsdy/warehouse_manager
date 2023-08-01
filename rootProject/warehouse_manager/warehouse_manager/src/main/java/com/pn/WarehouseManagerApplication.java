package com.pn;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

//@EnableCaching
@MapperScan(basePackages = {"com.pn.mapper"})
@SpringBootApplication
public class WarehouseManagerApplication {

    public static void main(String[] args) {

        SpringApplication.run(WarehouseManagerApplication.class, args);
    }

}
