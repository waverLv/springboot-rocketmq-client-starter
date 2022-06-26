package com.rocketmq.lv.extract;

import com.aliyun.openservices.ons.api.ConsumeContext;
import com.aliyun.openservices.ons.api.Message;
import com.rocketmq.lv.annotation.ListenPoint;
import org.springframework.stereotype.Service;

/**
 * @ProjectName: BidAmountExtract
 * @Author: lvminghui
 * @Description: 中标金额提取器
 * @Date: 2022/5/28 17:11
 * @Version: 1.0
 */
@Service
public class BidAmountExtract {

    @ListenPoint(topic = "PUBLISH_DATA_CHANGE",groupId = "CHANGE_GID")
    public void extract(Message message, ConsumeContext consumeContext){
        System.out.println(message);
    }
}
