package com.watkins.http.elastic;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.TimeZone;
import java.util.UUID;

public class ElasticClient {
    public final Logger LOGGER = LoggerFactory.getLogger(ElasticClient.class);
    private String index;
    private String type;

    @Autowired
    RestHighLevelClient client;

    public ElasticClient(String index, String type) {
        this.index = index;
        this.type = type;

        LOGGER.info("Created ElasticClient with " +
                "\nindex: " + this.index +
                "\ntype: " + this.type
        );
    }


    public String indexIsmpDocument(String ismpMessageJson) {
        Date date = new Date(Instant.now().getEpochSecond()*1000);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        IndexRequest indexRequest = new IndexRequest(this.index + "-" + sdf.format(date), this.type,
                UUID.randomUUID().toString()).source(ismpMessageJson, XContentType.JSON);
        String indexResponseResultName = "";
        try {
            IndexResponse indexResponse = client.index(indexRequest, RequestOptions.DEFAULT);
            indexResponseResultName = indexResponse.getResult().name();
            LOGGER.debug("Indexing json document to elasticsearch: " + ismpMessageJson);
        } catch (IOException e) {
            LOGGER.error("Elastic indexing attempted and failed. --> " + e.getMessage());
//            e.printStackTrace();
        }
        return indexResponseResultName;
    }


    public String convertMessageToJson(String message) {
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonInString = "";
        try {
            jsonInString = objectMapper.writeValueAsString(message);
        } catch (JsonProcessingException e) {
            LOGGER.error("JSON conversion attempted and failed. --> " + e.getMessage());
//            e.printStackTrace();
        }
        LOGGER.debug("Message as JSON -> " + jsonInString);

        return jsonInString;
    }
}