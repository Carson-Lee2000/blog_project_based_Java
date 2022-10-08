package com.lks.blog.blog_project.event;

import com.alibaba.fastjson.JSONObject;
import com.lks.blog.blog_project.entity.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class EventProducer {

    private final KafkaTemplate kafkaTemplate;

    @Autowired
    public EventProducer(KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    // 处理事件，本质上是生产发送的消息
    public void fireEvent(Event event) {
        // 将事件发送到指定的topic
        kafkaTemplate.send(event.getTopic(), JSONObject.toJSONString(event));
    }

}
