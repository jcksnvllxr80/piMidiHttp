package com.watkins.http.config;


import com.watkins.http.elastic.ElasticClient;
import com.watkins.http.msgHandlers.ElasticUtilities;
import com.watkins.http.messaging.LatestResponse;
import com.watkins.http.messaging.JmsConsumer;
import com.watkins.http.messaging.JmsProducer;
import com.watkins.http.parser.Parser;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.qpid.client.PooledConnectionFactory;
import org.apache.qpid.url.URLSyntaxException;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.support.destination.DestinationResolver;
import org.springframework.jms.support.destination.DynamicDestinationResolver;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Session;


@Configuration
@EnableJms
public class ApplicationConfig {
    private JmsConfig jmsConfig;
    private ParserConfig parserConfig;
    private SamConfig samConfig;
    private ElasticConfig elasticConfig;


    public ApplicationConfig(ParserConfig parserConfig, JmsConfig jmsConfig, SamConfig samConfig,
                             ElasticConfig elasticConfig) {
        this.jmsConfig = jmsConfig;
        this.parserConfig = parserConfig;
        this.samConfig = samConfig;
        this.elasticConfig = elasticConfig;
    }


    @Bean
    public LatestResponse createLatestResponse() {
        return new LatestResponse();
    }


    @Bean
    public ConnectionFactory connectionFactory() {
        PooledConnectionFactory factory = new PooledConnectionFactory();
        try {
            factory.setConnectionURLString("amqp://" + this.jmsConfig.getBrokerUsername() + ":" + this.jmsConfig.getBrokerPassword()
                    + "@client/default?brokerlist='tcp://" + this.jmsConfig.getHost() + "'");
        } catch (URLSyntaxException e) {
            e.printStackTrace();
        }
        factory.setConnectionTimeout(this.jmsConfig.getConnnectionTimeout());
        return factory;
    }


    @Bean
    public DestinationResolver destinationResolver() {
        DestinationResolver destination = new DynamicDestinationResolver();
        try {
            ConnectionFactory factory = connectionFactory();
            Connection connection = factory.createConnection();
            connection.setExceptionListener(exp -> {
                exp.printStackTrace(System.out);
                System.exit(1);
            });
            connection.start();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            destination.resolveDestinationName(session, this.jmsConfig.getConsumerQueue(), this.jmsConfig.isPubSubDomain());
        } catch (JMSException e) {
            e.printStackTrace();
        }
        return destination;
    }


    @Bean
    public DefaultJmsListenerContainerFactory myJmsListenerContainerFactory() {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory());
        factory.setDestinationResolver(destinationResolver());
        factory.setSessionTransacted(true);
        factory.setConcurrency("5");
        return factory;
    }


    @Bean
    public Parser createParser() {
        return new Parser(this.parserConfig.getKeystorePath(), this.samConfig.getSourceEmpAddress(), this.parserConfig.getKeystorePassword(),
                this.parserConfig.getStatusPropertiesList(), this.samConfig.getUsername(), this.jmsConfig.getDestinationEmpAddress(), this.parserConfig.getScac());
    }


    @Bean
    public JmsProducer createProducer() {
        return new JmsProducer(this.jmsConfig.getHost(), this.jmsConfig.getProducerQueue(), this.jmsConfig.getBrokerUsername(),
                this.jmsConfig.getBrokerPassword(), this.jmsConfig.getConnnectionTimeout());
    }


    @Bean
    public JmsConsumer createConsumer() {
        return new JmsConsumer();
    }


    @Bean(destroyMethod = "close")
    public RestHighLevelClient restClient() {
        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(
                this.elasticConfig.getUsername(), this.elasticConfig.getPassword()));
        RestClientBuilder builder = RestClient.builder(new HttpHost(this.elasticConfig.getHost(), this.elasticConfig.getPort()))
                .setHttpClientConfigCallback(httpClientBuilder -> httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider));
        RestHighLevelClient client = new RestHighLevelClient(builder);
        return client;
    }


    @Bean
    public ElasticClient elasticClient(){
        return new ElasticClient(this.elasticConfig.getIndex(), this.elasticConfig.getType());
    }


    @Bean
    public ElasticUtilities elasticUtilities(){
        return new ElasticUtilities();
    }
}
