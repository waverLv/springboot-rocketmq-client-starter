package com.rocketmq.lv.factory;

import com.aliyun.openservices.ons.api.MessageListener;
import com.rocketmq.lv.client.ListenerPoint;

import java.util.List;

/**
 * message listener 工厂类
 */
public interface MessageListenerFactory {

    public MessageListener newMessageListener(List<ListenerPoint> annotationListeners);
}
