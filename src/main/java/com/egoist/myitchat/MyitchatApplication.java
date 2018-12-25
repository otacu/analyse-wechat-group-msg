package com.egoist.myitchat;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
@MapperScan("com.egoist.myitchat.dao")//将项目中对应的mapper类的路径加进来就可以了
public class MyitchatApplication {

    public static void main(String[] args) {
        final ApplicationContext applicationContext = SpringApplication.run(MyitchatApplication.class, args);
        Wechat wechat = (Wechat) applicationContext.getBean(Wechat.class);
        wechat.start();
    }
}