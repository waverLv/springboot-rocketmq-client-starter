# 工程简介
    本项目是以阿里云RocketMQ消息服务的客户端依赖为基础开发封装的starter。主要用来简化配置，快速开发。
    项目采用自定义方法注解@ListenPoint，可以直接定位在业务核心方法上。注解目前包含三个属性，分别是topic、groupid、tags,topic指订阅的主题，groupId为主题下的分组，tags为主题下过滤消息的标签，三个属性搭配使用已基本满足订阅的业务场景。另外在RocketMQ单实例，多个主题订阅的场景下，starter根据注解的配置已自动实现路由，但是当订阅的消息分别来自不同的RocketMQ实例或者账号的场景下，目前并未扩展。


# 延伸阅读
   计划下一步开发producer-starter,以及consumer端多实例场景下的简便实用方式。

