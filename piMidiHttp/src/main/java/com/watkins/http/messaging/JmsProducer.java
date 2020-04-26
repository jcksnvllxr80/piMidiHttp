package com.watkins.http.messaging;

import org.apache.qpid.client.PooledConnectionFactory;
import org.apache.qpid.url.URLSyntaxException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.*;
import java.util.Arrays;

public class JmsProducer implements Producer {
    private final Logger  LOGGER = LoggerFactory.getLogger(JmsProducer.class);
    private static final int DELIVERY_MODE = DeliveryMode.NON_PERSISTENT;
    private String broker;
    private String destQueue;
    private String brokerUsername;
    private String brokerPassword;
    private Integer connectionTimeout;
    private JmsTemplate jmsTemplate;


    public JmsProducer(String broker, String destQueue, String brokerUsername, String brokerPassword, int connectionTimeout) {
        this.broker = broker;
        this.destQueue = destQueue;
        this.brokerUsername = brokerUsername;
        this.brokerPassword = brokerPassword;
        this.connectionTimeout = connectionTimeout;
        LOGGER.info("Created Parser with " +
                "\nbroker: " + this.broker +
                "\ndestQueue: " + this.destQueue +
                "\nbrokerUsername: " + this.brokerUsername +
                "\nbrokerPassword: " + this.brokerPassword
        );
        this.jmsTemplate = new JmsTemplate(connectionFactory());
        this.jmsTemplate.setDefaultDestinationName(this.destQueue);
    }


    public ConnectionFactory connectionFactory() {
        PooledConnectionFactory factory = new PooledConnectionFactory();
        try {
            factory.setConnectionURLString("amqp://" + this.brokerUsername + ":" + this.brokerPassword
                    + "@client/default?brokerlist='tcp://" + this.broker + "'");
        } catch (URLSyntaxException e) {
            e.printStackTrace();
        }
        factory.setConnectionTimeout(this.connectionTimeout);
        return factory;
    }


    @Override
    public void sendEmpMessage(String message) {
        LOGGER.debug("\nEMP byte array (before being sent) as hex string:\n" + message + "\n" +
                "\nEMP byte array (before being sent) as dec byte[]:\n" + message + "\n");
        try {
            this.jmsTemplate.convertAndSend(message);
        } catch (Exception exp) {
            LOGGER.warn("Caught exception. Message may not have been sent.", exp.getCause());
            exp.printStackTrace(System.out);
//            System.exit(1);
        }
    }
}
