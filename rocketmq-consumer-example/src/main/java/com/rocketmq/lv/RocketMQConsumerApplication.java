package com.rocketmq.lv;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @ProjectName: RocketMQConsumerApplication
 * @Author: lvminghui
 * @Description: 启动类
 * @Date: 2022/5/28 16:29
 * @Version: 1.0
 */
@SpringBootApplication
public class RocketMQConsumerApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(RocketMQConsumerApplication.class, args);
    }
}
