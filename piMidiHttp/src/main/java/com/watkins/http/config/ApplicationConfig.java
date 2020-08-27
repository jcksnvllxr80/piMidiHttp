package com.watkins.http.config;

import com.watkins.http.elastic.ElasticClient;
import com.watkins.http.msgHandlers.ElasticUtilities;
import com.watkins.http.messaging.LatestResponse;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {
    private final ElasticConfig elasticConfig;

    @Autowired
    public ApplicationConfig(ElasticConfig elasticConfig) {
        this.elasticConfig = elasticConfig;
    }


    @Bean
    public LatestResponse createLatestResponse() {
        return new LatestResponse();
    }


    @Bean(destroyMethod = "close")
    public RestHighLevelClient restClient() {
        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(
                this.elasticConfig.getUsername(), this.elasticConfig.getPassword()));
        RestClientBuilder builder = RestClient.builder(new HttpHost(this.elasticConfig.getHost(), this.elasticConfig.getPort()))
                .setHttpClientConfigCallback(httpClientBuilder -> httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider));
        return new RestHighLevelClient(builder);
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
