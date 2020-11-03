package com.hd.test.kafka;

import com.hd.test.common.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author houdu
 * @date 2020/8/14
 */
@Slf4j
@RestController
public class Test {

    @Autowired
    private KafkaProducer kafkaProducer;

    private static final AtomicInteger AI = new AtomicInteger(1);

    @GetMapping("test")
    public void test() {
        int andIncrement = AI.getAndIncrement();
        GenericMessage<String> msg = new GenericMessage(String.valueOf(andIncrement));
        Message message = new Message();
        message.setId("KFK_" + System.currentTimeMillis());
        message.setMsg(UUID.randomUUID().toString());
        message.setSendTime(new Date());
        log.info("send kafka message {}!", message.toString());
        kafkaProducer.send(JsonUtils.toJsonEx(message));
    }

    @GetMapping("test1")
    public void test1() {
        int andIncrement = AI.getAndIncrement();
        log.info("send kafka message {}!", andIncrement);
        kafkaProducer.send(String.valueOf(andIncrement));
    }

//    @Scheduled(cron = "0/10 * * * * ? ")
    public void testScheduled() {
        Message message = new Message();
        message.setId("KFK_" + AI.getAndIncrement());
        message.setMsg(UUID.randomUUID().toString());
        message.setSendTime(new Date());
        log.info("send kafka message {}!", message.toString());
        kafkaProducer.send(JsonUtils.toJsonEx(message));
    }
}
