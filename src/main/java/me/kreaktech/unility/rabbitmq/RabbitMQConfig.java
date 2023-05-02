package me.kreaktech.unility.rabbitmq;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.config.RetryInterceptorBuilder;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

@Configuration
public class RabbitMQConfig {

    private static final String QUEUE_NAME = System.getenv("RABBITMQ_QUEUE");
    private static final String USERNAME = System.getenv("RABBITMQ_DEFAULT_USER");
    private static final String PASSWORD = System.getenv("RABBITMQ_DEFAULT_PASS");
    private final Receiver receiver;
    @Autowired
    public RabbitMQConfig(Receiver receiver) {
        this.receiver = receiver;
    }

    // Configuration for RabbitMQ connection
    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory("rabbitmq");
        connectionFactory.setUsername(USERNAME);
        connectionFactory.setPassword(PASSWORD);

        connectionFactory.setRequestedHeartBeat(30); // In seconds
        return connectionFactory;
    }

    // Configuration for the RabbitMQ message listener container
    @Bean
    public SimpleMessageListenerContainer listenerContainer(ConnectionFactory connectionFactory,
                                                             MessageListenerAdapter listenerAdapter) {
        SimpleMessageListenerContainer listenerContainer = new SimpleMessageListenerContainer();
        listenerContainer.setConnectionFactory(connectionFactory);
        listenerContainer.setQueueNames(QUEUE_NAME);
        listenerContainer.setMessageListener(listenerAdapter);
        listenerContainer.setConcurrentConsumers(1);
        listenerContainer.setMaxConcurrentConsumers(1);
        listenerContainer.setDefaultRequeueRejected(true);
        listenerContainer.setPrefetchCount(1);
        listenerContainer.setAdviceChain(RetryInterceptorBuilder
                .stateless()
                .maxAttempts(3)
                .backOffOptions(1000, 2, 10000) // wait 1s, 2x backoff, up to 10s
                .build());
        return listenerContainer;
    }

    // Configuration for the RabbitMQ message listener adapter
    @Bean
    public MessageListenerAdapter listenerAdapter() {
        return new MessageListenerAdapter(receiver, "handleMessage");
    }

    // Configuration for the RabbitMQ queue
    @Bean
    public Queue queue() {
        return QueueBuilder.durable(QUEUE_NAME)
                .withArgument("x-dead-letter-exchange", "")
                .withArgument("x-dead-letter-routing-key", QUEUE_NAME + "-retry")
                .build();
    }

    // Event listener to start processing events after Spring has fully initialized
    @EventListener(ApplicationReadyEvent.class)
    public void startProcessingEvents() {
        listenerContainer(connectionFactory(), listenerAdapter());
    }
}