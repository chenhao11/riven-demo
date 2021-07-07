package club.mq.rabbitmq.producer;

import club.mq.rabbitmq.model.BaseProjectMq;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.util.UUID;

/**
 * @author riven
 * @date 2021-07-07 15:20
 */

public class MessageProducer implements RabbitTemplate.ConfirmCallback {

    /**
     * 由于rabbitTemplate的scope属性设置为ConfigurableBeanFactory.SCOPE_PROTOTYPE，所以不能自动注入
     */
    private RabbitTemplate rabbitTemplate;

    /**
     * 构造方法注入rabbitTemplate
     */
    public MessageProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
        // rabbitTemplate如果为单例的话，那回调就是最后设置的内容
        rabbitTemplate.setConfirmCallback(this);
    }

    /**
     * 向队列发送消息，默认exchange为""，routingKey即为queueName。
     *
     * @param queueName     队列名称
     * @param baseProjectMq 发送内容
     */
    public void sendDirect(String queueName, BaseProjectMq baseProjectMq) {
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        rabbitTemplate.convertAndSend(queueName, baseProjectMq, correlationData);
    }

    /**
     * 向队列发送消息，默认exchange为""，routingKey即为queueName。
     *
     * @param queueName 队列名称
     * @param content   发送内容
     */
    public void sendDirect(String queueName, Object content) {
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        rabbitTemplate.convertAndSend(queueName, content, correlationData);
    }

    /**
     * 向队列发送阻塞消息，上条消息处理完，下条才能发送，默认exchange为""，routingKey即为queueName。
     *
     * @param queueName 队列名称
     * @param content   发送内容
     */
    public void sendBlockDirect(String queueName, BaseProjectMq content) {
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        rabbitTemplate.convertSendAndReceive(queueName, content, correlationData);
    }

    /**
     * 向队列发送阻塞消息，上条消息处理完，下条才能发送，默认exchange为""，routingKey即为queueName。
     *
     * @param queueName 队列名称
     * @param content   发送内容
     */
    public void sendBlockDirect(String queueName, Object content) {
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        rabbitTemplate.convertSendAndReceive(queueName, content, correlationData);
    }

    /**
     * 向延迟队列发送消息
     * <p>
     * 注意：
     * 1. 一定要安装Rabbitmq的延迟插件，不然不生效（码云有安装文档）
     * 2. 交换机不能是默认交换机且交换机一定要将其delayed参数设置为"true"（字符串类型）
     *
     * @param exchangeName 交换机名称
     * @param key          路由键
     * @param content      发送内容
     * @param delayTime    延迟发送时间（毫秒，范围0 ～（2 ^ 32）-1）
     */
    public void sendDelayedDirect(String exchangeName, String key, Object content, Integer delayTime) {
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        MessagePostProcessor messagePostProcessor = message -> {
            message.getMessageProperties().setDeliveryMode(MessageDeliveryMode.PERSISTENT);
            message.getMessageProperties().setDelay(delayTime);
            return message;
        };
        rabbitTemplate.convertAndSend(exchangeName, key, content, messagePostProcessor, correlationData);
    }

    /**
     * Fanout exchange 发送消息 不指定routingKey，只向该交换机绑定的所有队列广播消息
     *
     * @param exchangeName 交换机名称
     * @param content      发送内容
     */
    public void sendFanout(String exchangeName, BaseProjectMq content) {
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        rabbitTemplate.convertAndSend(exchangeName, "", content, correlationData);
    }

    /**
     * 向交换机广播消息，交换机需绑定不同的队列，绑定该交换机的队列只接受一次消息
     *
     * @param exchangeName 交换机名称
     * @param content      发送内容
     */
    public void sendFanout(String exchangeName, Object content) {
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        rabbitTemplate.convertAndSend(exchangeName, "", content, correlationData);
    }

    /**
     * 向交换机广播消息，交换机需绑定不同的队列，绑定该交换机的队列只接受一次消息，指定路由键
     *
     * @param exchangeName 交换机名称
     * @param key          路由键
     * @param content      发送内容
     */
    public void sendTopic(String exchangeName, String key, BaseProjectMq content) {
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        rabbitTemplate.convertAndSend(exchangeName, key, content, correlationData);
    }

    /**
     * 向交换机广播消息，交换机需绑定不同的队列，绑定该交换机的队列只接受一次消息，指定路由键
     *
     * @param exchangeName 交换机名称
     * @param key          路由键
     * @param content      发送内容
     */
    public void sendTopic(String exchangeName, String key, Object content) {
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        rabbitTemplate.convertAndSend(exchangeName, key, content, correlationData);
    }


    /**
     * 生产方消息发送确认
     *
     * @param correlationData 每个发送的消息都需要配备一个 CorrelationData 相关数据对象，CorrelationData 对象内部只有一个 id 属性，用来表示当前消息唯一性
     * @param b               消息发送结果
     * @param s               失败原因
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean b, String s) {

    }
}
