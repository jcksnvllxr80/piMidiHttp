package com.watkins.http.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(value="sam.jms")
public class JmsConfig {
    private String host;
    private String consumerQueue;
    private String producerQueue;
    private String destinationEmpAddress;
    private String brokerUsername;
    private String brokerPassword;
    private int connnectionTimeout;

    public boolean isPubSubDomain() {
        return pubSubDomain;
    }

    public void setPubSubDomain(boolean pubSubDomain) {
        this.pubSubDomain = pubSubDomain;
    }

    private boolean pubSubDomain;

    public int getConnnectionTimeout() {
        return connnectionTimeout;
    }

    public void setConnnectionTimeout(int connnectionTimeout) {
        this.connnectionTimeout = connnectionTimeout;
    }

    public String getDestinationEmpAddress() {
        return destinationEmpAddress;
    }

    public void setDestinationEmpAddress(String destinationEmpAddress) {
        this.destinationEmpAddress = destinationEmpAddress;
    }

    public String getBrokerUsername() {
        return brokerUsername;
    }

    public void setBrokerUsername(String brokerUsername) {
        this.brokerUsername = brokerUsername;
    }

    public String getBrokerPassword() {
        return brokerPassword;
    }

    public void setBrokerPassword(String brokerPassword) {
        this.brokerPassword = brokerPassword;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getConsumerQueue() {
        return consumerQueue;
    }

    public void setConsumerQueue(String consumerQueue) {
        this.consumerQueue = consumerQueue;
    }

    public String getProducerQueue() {
        return producerQueue;
    }

    public void setProducerQueue(String producerQueue) {
        this.producerQueue = producerQueue;
    }
}
