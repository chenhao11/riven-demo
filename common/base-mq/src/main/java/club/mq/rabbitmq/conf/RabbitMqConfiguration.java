package club.mq.rabbitmq.conf;

import club.mq.rabbitmq.producer.MessageProducer;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author riven
 * @date 2021-07-07 15:10
 */
@Configuration
public class RabbitMqConfiguration {

    @Bean(name = "rabbitMqConnectionFactory")
    public ConnectionFactory connectionFactory() {

        String addresses = "127.0.0.1:5672";
        String username = "guest";
        String password = "guest";
        String virtualHost = "";
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setAddresses(addresses);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        if (StringUtils.isNotEmpty(virtualHost)) {
            connectionFactory.setVirtualHost(virtualHost);
        } else {
            connectionFactory.setVirtualHost("/");
        }
        // 开启confirm模式
        //connectionFactory.setPublisherConfirms(true);
        connectionFactory.setPublisherConfirmType(CachingConnectionFactory.ConfirmType.NONE);
        connectionFactory.setChannelCacheSize(100);
//        connectionFactory.setChannelCheckoutTimeout(1500);
        return connectionFactory;
    }


    @Bean(name = "customerListenerContainerFactory")
    public SimpleRabbitListenerContainerFactory containerFactory(@Qualifier("rabbitMqConnectionFactory") ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        // 设置当rabbitmq收到nack/reject确认信息时的处理方式，设为true，扔回queue头部，设为false，丢弃。
        factory.setDefaultRequeueRejected(false);
        // 设置消费方应答方式
        factory.setAcknowledgeMode(AcknowledgeMode.NONE);
        // 实现ErrorHandler接口设置进去，所有未catch的异常都会由ErrorHandler处理
//        factory.setErrorHandler(throwable -> log.info("RabbitMQ,消费报错:", throwable.getCause()));
        // 增加入队和确认速度
        factory.setPrefetchCount(128);
        factory.setReceiveTimeout(15000L);
        // 增加出队速度
        // 设置线程数
        factory.setConcurrentConsumers(128);
        // 最大线程数
        factory.setMaxConcurrentConsumers(256);
        // 消息序列化类型
//        factory.setMessageConverter(new Jackson2JsonMessageConverter());
        factory.setConnectionFactory(connectionFactory);
        return factory;
    }

    @Bean
    public MessageProducer messageProducer(@Qualifier("rabbitMqConnectionFactory") ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        return new MessageProducer(rabbitTemplate);
    }


}
