package club.mq.rabbitmq.consumer;

import club.mq.rabbitmq.model.BaseProjectMq;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

/**
 * 注意消费类上面这个@Service注解不可少
 *
 * @author riven
 * @date 2021-07-07 15:41
 */
@Service
public class RabbitConsumer {

    @RabbitListener(queuesToDeclare = @Queue("DIRECT_QUEUE"))
    public void directConsumer(BaseProjectMq msg) {
        System.out.println("消息被消费");
        System.out.println(msg.getData());
    }
}
