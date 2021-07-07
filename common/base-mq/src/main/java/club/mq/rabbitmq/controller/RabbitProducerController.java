package club.mq.rabbitmq.controller;

import club.mq.rabbitmq.model.BaseProjectMq;
import club.mq.rabbitmq.producer.MessageProducer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import static club.mq.rabbitmq.constant.RabbitConstant.DIRECT_QUEUE;

/**
 * @author riven
 * @date 2021-07-07 15:35
 */
@RestController
@RequestMapping(value = "/rabbit")
public class RabbitProducerController {

    @Resource
    MessageProducer messageProducer;

    @GetMapping("/direct")
    public Object direct() {
        messageProducer.sendDirect(DIRECT_QUEUE, new BaseProjectMq("AAA", "111"));

        return "发送直连队列成功";
    }
}
