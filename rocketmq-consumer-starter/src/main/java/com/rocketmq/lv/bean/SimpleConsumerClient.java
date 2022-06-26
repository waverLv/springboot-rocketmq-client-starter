package com.rocketmq.lv.bean;

import com.aliyun.openservices.ons.api.ConsumeContext;
import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.MessageListener;
import com.aliyun.openservices.ons.api.PropertyKeyConst;
import com.aliyun.openservices.ons.api.bean.ConsumerBean;
import com.aliyun.openservices.ons.api.bean.Subscription;
import com.rocketmq.lv.annotation.ListenPoint;
import com.rocketmq.lv.client.ListenerPoint;
import com.rocketmq.lv.config.RocketMQConsumerConfiguration;
import com.rocketmq.lv.factory.MessageListenerFactory;
import com.rocketmq.lv.util.BeanUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @ProjectName: SimpleConsumerClient
 * @Author: lvminghui
 * @Description: 简单消费客户端
 * @Date: 2022/5/27 15:29
 * @Version: 1.0
 */
public class SimpleConsumerClient{
    private final static Logger logger = LoggerFactory.getLogger(RocketMQConsumerConfiguration.class);
    //RocketMQ消费者列表
    private List<ConsumerBean> consumerBeans = new ArrayList<>();
    private RocketMQConsumerProperties consumerProperties;
    //MessageListener工厂类
    private MessageListenerFactory messageListenerFactory;
    //listenPoint注解方法缓存
    private Map<String,ListenPoint> listenPointMap = new HashMap<>();


    public SimpleConsumerClient(RocketMQConsumerProperties consumerProperties, MessageListenerFactory messageListenerFactory){
        this.consumerProperties = consumerProperties;
        this.messageListenerFactory = messageListenerFactory;
        init();
    }

    private void init(){
        logger.info("初始化启动消费者开始！");
        List<ListenerPoint> listenerPoints = fetchListenerPoints();
        annotationListenersValidation(listenerPoints);
        consumerBeans = listenerPoints.stream().map(listenerPoint -> {
            return buildConsumerBean(listenerPoint);
        }).collect(Collectors.toList());

    }

    /**
     * 获取所有添加@ListenPoint注解的方法
     * @return
     */
    public List<ListenerPoint> fetchListenerPoints(){
        List<ListenerPoint> listenerPoints = new ArrayList<>();
        Map<String, Object> beanMap = BeanUtil.getBeansWithAnnotation(Component.class);
        if (beanMap != null) {
            for (Object target : beanMap.values()) {
                Method[] methods = target.getClass().getDeclaredMethods();
                if (methods != null && methods.length > 0) {
                    for (Method method : methods) {
                        ListenPoint lp = AnnotationUtils.findAnnotation(method, ListenPoint.class);
                        if (lp != null  && listenPointMap.get(getListenPointUniqueKey(lp)) == null) {
                            listenerPoints.add(new ListenerPoint(target, method, lp));
                        }
                    }
                }
            }
        }
        return listenerPoints;
    }

    /**
     * 每个listenpoint注解创建一个uniquekey
     * @param lp
     * @return
     */
    private String getListenPointUniqueKey(ListenPoint lp){
        return lp.topic()+"#"+lp.groupId();
    }

    /**
     * 验证所有@ListenPoint方法是否为合法方法
     * @param annotationListeners
     */
    public void annotationListenersValidation(List<ListenerPoint> annotationListeners){
        annotationListeners.stream().forEach(listenerPoint -> {
            Method method = listenerPoint.getMethod();
            Class<?>[] parameterTypes = method.getParameterTypes();
            Arrays.asList(parameterTypes).stream().forEach(clazz -> {
                if(!(clazz.equals(Message.class) || clazz.equals(ConsumeContext.class))){
                    throw new RuntimeException("");
                }
            });
        });

    }

    public ConsumerBean buildConsumerBean(ListenerPoint listenerPoint){
        ConsumerBean consumerBean  = new ConsumerBean();
        //配置文件
        Properties properties = new Properties();
        properties.setProperty(PropertyKeyConst.NAMESRV_ADDR, consumerProperties.getNamesrvAddr());
        properties.setProperty(PropertyKeyConst.AccessKey,consumerProperties.getAccessKey());
        properties.setProperty(PropertyKeyConst.SecretKey,consumerProperties.getSecretKey());
        properties.setProperty(PropertyKeyConst.GROUP_ID, listenerPoint.getListenerPoint().groupId());
        //将消费者线程数 20为默认值
        int nCpu = Runtime.getRuntime().availableProcessors();
        properties.setProperty(PropertyKeyConst.ConsumeThreadNums, String.valueOf(nCpu));
        consumerBean.setProperties(properties);
        //订阅关系
        Map<Subscription,MessageListener> subscriptionTable = buildSubScriptionTable(listenerPoint);
        //订阅多个topic如上面设置
        consumerBean.setSubscriptionTable(subscriptionTable);
        return consumerBean;
    }

    /**
     * 创建订阅关系
     * @param listenerPoint
     * @return
     */
    public Map<Subscription,MessageListener> buildSubScriptionTable(ListenerPoint listenerPoint){
        Map<Subscription, MessageListener> subscriptionTable = new HashMap<Subscription, MessageListener>();
        MessageListener messageListener = messageListenerFactory.newMessageListener(Collections.singletonList(listenerPoint));
        Subscription subscription = new Subscription();
        subscription.setTopic(listenerPoint.getListenerPoint().topic());
        subscription.setExpression(listenerPoint.getListenerPoint().tags());
        subscriptionTable.put(subscription, messageListener);
        return subscriptionTable;
    }



    public void start(){
        if(!CollectionUtils.isEmpty(consumerBeans)){
            consumerBeans.stream().forEach(ConsumerBean::start);
        }
    }

    public void destroy(){
        if(!CollectionUtils.isEmpty(consumerBeans)){
            consumerBeans.stream().forEach(ConsumerBean::shutdown);
        }
    }


}
