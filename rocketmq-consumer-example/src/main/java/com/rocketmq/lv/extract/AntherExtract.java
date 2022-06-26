package com.rocketmq.lv.extract;

import com.aliyun.openservices.ons.api.ConsumeContext;
import com.aliyun.openservices.ons.api.Message;
import com.rocketmq.lv.annotation.ListenPoint;
import org.springframework.stereotype.Service;

/**
 * @ProjectName: AntherExtract
 * @Author: lvminghui
 * @Description: 另一个
 * @Date: 2022/6/7 9:51
 * @Version: 1.0
 */
@Service
public class AntherExtract {
    @ListenPoint(topic = "ANTHOR_PUBLISH_DATA_CHANGE",groupId = "ANTHOR_CHANGE_GID")
    public void extract(Message message, ConsumeContext consumeContext){
        System.out.println(message);
    }
}
