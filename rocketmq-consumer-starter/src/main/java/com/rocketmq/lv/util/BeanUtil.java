package com.rocketmq.lv.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.lang.annotation.Annotation;
import java.util.*;

/**
 * @author lvminghui
 * @date 2022/05/31
 */
public class BeanUtil implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        BeanUtil.applicationContext = applicationContext;
    }

    public static <T> T getBean(Class<T> clazz) {
        T obj;
        try {
            obj = applicationContext.getBean(clazz);
        } catch (Exception e) {
            obj = null;
        }
        return obj;
    }

    public static <T> List<T> getBeansOfType(Class<T> clazz) {
        Map<String, T> map;
        try {
            map = applicationContext.getBeansOfType(clazz);
        } catch (Exception e) {
            map = null;
        }
        return map == null ? null : new ArrayList<>(map.values());
    }

    public static Map<String, Object> getBeansWithAnnotation(Class<? extends Annotation> anno) {
        Map<String, Object> map;
        try {
            map = applicationContext.getBeansWithAnnotation(anno);
        } catch (Exception e) {
            map = null;
        }
        return map;
    }

    public static Map<String,Object> getBeans(){
        String[] beanNames = applicationContext.getBeanDefinitionNames();
        final Map<String, Object> map = new HashMap<>();
        try {
            Arrays.asList(beanNames).stream().forEach(beanName->{
                map.put(beanName,applicationContext.getBean(beanName));
            });

        } catch (Exception e) {
           e.printStackTrace();
        }
        return map;
    }

}