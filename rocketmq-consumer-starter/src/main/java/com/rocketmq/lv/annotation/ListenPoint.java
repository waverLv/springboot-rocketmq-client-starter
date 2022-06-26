package com.rocketmq.lv.annotation;

import java.lang.annotation.*;

@Target(value = {ElementType.METHOD, ElementType.TYPE})
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface ListenPoint {
    //监听的TOPIC
    String topic();
    //监听的GOUPID
    String groupId();
    //监听的tag,默认为*,表示监听全部
    String tags() default "*";
}
