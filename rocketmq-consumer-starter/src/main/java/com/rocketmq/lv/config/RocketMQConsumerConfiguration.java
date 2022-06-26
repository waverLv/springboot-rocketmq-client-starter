package com.rocketmq.lv.config;

import com.rocketmq.lv.bean.RocketMQConsumerProperties;
import com.rocketmq.lv.bean.SimpleConsumerClient;
import com.rocketmq.lv.factory.MessageFactories;
import com.rocketmq.lv.util.BeanUtil;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ProjectName: RocketMQConsumerConfiguration
 * @Author: lvminghui
 * @Description: consumer配置类
 * @Date: 2022/5/27 15:20
 * @Version: 1.0
 */
@Configuration
public class RocketMQConsumerConfiguration {

    @Bean
    public BeanUtil beanUtil(){
        return new BeanUtil();
    }

    
    @Bean(initMethod = "start",destroyMethod = "destroy")
    public SimpleConsumerClient buildConsumerClient(RocketMQConsumerProperties consumerProperties) {
        SimpleConsumerClient client = new SimpleConsumerClient(consumerProperties, MessageFactories.defaultMessageListenFactory());
        return client;
    }

    @Bean
    @ConfigurationProperties(prefix = "rocketmq.consumer")
    public RocketMQConsumerProperties rocketMQConsumerProperties(){
        return new RocketMQConsumerProperties();
    }


}
