package com.yonyou.einvoice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DataTransferApplication {

  public static void main(String[] args) {
    SpringApplication.run(DataTransferApplication.class, args);
  }

}
