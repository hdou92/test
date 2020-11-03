package com.hd.test.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * 类功能描述：<br>
 * <ul>
 * <li>类功能描述1<br>
 * <li>类功能描述2<br>
 * <li>类功能描述3<br>
 * </ul>
 * 修改记录：<br>
 * <ul>
 * <li>修改记录描述1<br>
 * <li>修改记录描述2<br>
 * <li>修改记录描述3<br>
 * </ul>
 *
 * @author xuefl
 * @version 5.0 since 2020-01-13
 */
@Slf4j
@Component
public class KafkaConsumer {

    @KafkaListener(topics = {KafkaProducer.TOPIC_TEST}, groupId = KafkaProducer.TOPIC_GROUP1)
    public void topicTest1(ConsumerRecord<?, ?> msg) {
        Optional<Object> message = Optional.ofNullable(msg);
        if (message.isPresent()) {
            log.info("topicTest1 消费了： Topic: {}  Message: {}" ,KafkaProducer.TOPIC_TEST ,  msg);
        }
    }

    @KafkaListener(topics = {KafkaProducer.TOPIC_TEST}, groupId = KafkaProducer.TOPIC_GROUP2)
    public void topicTest2(ConsumerRecord<?, ?> msg) {
        Optional<Object> message = Optional.ofNullable(msg);
        if (message.isPresent()) {
            log.info("topicTest2 消费了： Topic: {}  Message: {}" ,KafkaProducer.TOPIC_TEST ,  msg);
        }
    }

}