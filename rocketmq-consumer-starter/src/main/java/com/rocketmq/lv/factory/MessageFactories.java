package com.rocketmq.lv.factory;

/**
 * @ProjectName: MessageFactories
 * @Author: lvminghui
 * @Description: 撒
 * @Date: 2022/5/27 15:40
 * @Version: 1.0
 */
public class MessageFactories {

    public static MessageListenerFactory defaultMessageListenFactory(){
        return new DefaultMessageListenerFactory();
    }
}
