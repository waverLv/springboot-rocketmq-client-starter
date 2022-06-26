package com.rocketmq.lv.listener;

import com.rocketmq.lv.client.ListenerPoint;

import java.util.List;

/**
 * @ProjectName: MessageListenerWrapper
 * @Author: lvminghui
 * @Description: message listener 包装类
 * @Date: 2022/5/27 15:07
 * @Version: 1.0
 */
public class MessageListenerWrapper extends AbstractMessageListener {

    public MessageListenerWrapper(List<ListenerPoint> annotationListeners){
        super.setAnnotationListeners(annotationListeners);
    }
}
