package com.nsulzh.knowledge;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.nsulzh.knowledge")
@MapperScan("com.nsulzh.knowledge.dao")
public class KnowledgeApplication {

    public static void main(String[] args) {
        SpringApplication.run(KnowledgeApplication.class, args);
    }

}
