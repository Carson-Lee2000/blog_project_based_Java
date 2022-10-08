package com.lks.blog.blog_project.event;

import com.alibaba.fastjson.JSONObject;
import com.lks.blog.blog_project.entity.Event;
import com.lks.blog.blog_project.entity.Message;
import com.lks.blog.blog_project.service.MessageService;
import com.lks.blog.blog_project.util.CommunityConstant;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class EventConsumer implements CommunityConstant {

    private static final Logger logger = LoggerFactory.getLogger(EventConsumer.class);

    // 需要向message表中写入数据
    private final MessageService messageService;

    @Autowired
    public EventConsumer(MessageService messageService) {
        this.messageService = messageService;
    }

    // 消费事件
    @KafkaListener(topics = {TOPIC_COMMENT, TOPIC_LIKE, TOPIC_FOLLOW})
    public void handleCommentMessage(ConsumerRecord record) {
        if (record == null || record.value() == null) {
            logger.error("消息的内容为空！");
            return;
        }

        Event event = JSONObject.parseObject(record.value().toString(), Event.class);
        if (event == null) {
            logger.error("消息格式错误！");
            return;
        }

        // 发送站内通知，将一条系统通知插入到message表
        Message message = new Message();
        message.setFromId(SYSTEM_USER_ID);

    }
}
