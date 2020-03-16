package com.yonyou.einvoice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@MapperScan(value = "com.yonyou.einvoice.dao.UserMapper")
public class DataTransferApplication {

  public static void main(String[] args) {
    SpringApplication.run(DataTransferApplication.class, args);

  }

}
