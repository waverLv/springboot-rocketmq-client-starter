package com.rocketmq.lv.client;

import com.rocketmq.lv.annotation.ListenPoint;

import java.lang.reflect.Method;

/**
 * ListenerPoint
 * save the information of listener's method-info
 *
 * @author chen.qian
 * @date 2018/3/23
 */
public class ListenerPoint {
    private Object target;
    private Method method;
    private ListenPoint listenerPoint;

    public ListenerPoint(Object target, Method method, ListenPoint listenerPoint) {
        this.target = target;
        this.method = method;
        this.listenerPoint = listenerPoint;
    }

    public Object getTarget() {
        return target;
    }

    public Method getMethod() {
        return method;
    }

    public ListenPoint getListenerPoint() {
        return listenerPoint;
    }
}
