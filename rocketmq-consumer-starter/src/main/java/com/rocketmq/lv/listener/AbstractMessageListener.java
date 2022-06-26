package com.rocketmq.lv.listener;

import com.aliyun.openservices.ons.api.Action;
import com.aliyun.openservices.ons.api.ConsumeContext;
import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.MessageListener;
import com.rocketmq.lv.client.ListenerPoint;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

/**
 * @ProjectName: AbstractMessageListener
 * @Author: lvminghui
 * @Description: message listener抽象类
 * @Date: 2022/5/27 15:04
 * @Version: 1.0
 */
public abstract class AbstractMessageListener implements MessageListener {

    private List<ListenerPoint> annotationListeners;

    @Override
    public Action consume(Message message, ConsumeContext consumeContext) {
        if(CollectionUtils.isEmpty(annotationListeners)){
            throw new RuntimeException("");
        }
        try{
            for (ListenerPoint listenerPoint: annotationListeners) {
                listenerPoint.getMethod().invoke(listenerPoint.getTarget(),message,consumeContext);
            }
        }catch (Exception ex){
            ex.printStackTrace();
            return Action.ReconsumeLater;
        }

        return Action.CommitMessage;
    }





    public List<ListenerPoint> getAnnotationListeners() {
        return annotationListeners;
    }

    public void setAnnotationListeners(List<ListenerPoint> annotationListeners) {
        this.annotationListeners = annotationListeners;
    }
}
