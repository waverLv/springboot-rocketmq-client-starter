package com.rocketmq.lv.factory;

import com.aliyun.openservices.ons.api.MessageListener;
import com.rocketmq.lv.client.ListenerPoint;
import com.rocketmq.lv.listener.MessageListenerWrapper;

import java.util.List;

/**
 * @ProjectName: DefaultMessageListenerFactory
 * @Author: lvminghui
 * @Description: 默认的listener factory
 * @Date: 2022/5/27 15:01
 * @Version: 1.0
 */
public class DefaultMessageListenerFactory implements MessageListenerFactory {

    @Override
    public MessageListener newMessageListener(List<ListenerPoint> annotationListeners) {
        return new MessageListenerWrapper(annotationListeners);
    }
}
