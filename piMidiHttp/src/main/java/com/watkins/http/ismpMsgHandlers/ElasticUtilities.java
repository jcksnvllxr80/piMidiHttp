package com.watkins.http.ismpMsgHandlers;

import com.watkins.http.elastic.ElasticClient;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

public class ElasticUtilities extends MessagingHandlerUtilities{
    private final Logger LOGGER = LoggerFactory.getLogger(ElasticUtilities.class);

    @Autowired
    ElasticClient elasticClient;

    public ElasticUtilities() {
    }

    public void addJsonFieldsThenIndexElastic(String message, Map<String, String> fieldsToAdd){
        if (elasticClient == null){
            LOGGER.error("elasticClient is null");
        }

        try {
            elasticClient.indexIsmpDocument(addMessageSpecFields(
                    elasticClient.convertMessageToJson(message), fieldsToAdd)
            );
        } catch (NullPointerException e){
            LOGGER.warn("Caught NullPointerException connecting to Elasticsearch: " + e.getMessage());
            LOGGER.warn("Not connected to an Elasticsearch Cluster, will not index message.");
//            e.printStackTrace();
        }
    }


    public void addChangedFieldTypesThenIndexElastic(
            String message, Map<String, String> fieldsToAdd, List<String> fieldsToChange){
        try {
            elasticClient.indexIsmpDocument(addMessageSpecFields(
                    changeJsonFieldTypeToStr(elasticClient.convertMessageToJson(message), fieldsToChange), fieldsToAdd)
            );
        } catch (NullPointerException e){
            LOGGER.warn("Caught NullPointerException connecting to Elasticsearch: " + e.getMessage());
            LOGGER.warn("Not connected to an Elasticsearch Cluster, will not index message.");
//            e.printStackTrace();
        }
    }


    public String changeJsonFieldTypeToStr(String ismpJson, List<String> fieldsToChange) {
        JSONObject jsonObj = null;
        try {
            jsonObj = new JSONObject(ismpJson);
            //add fields in map to the json
            for (String k : fieldsToChange){
                String fieldsValue = String.valueOf(jsonObj.get(k));
                jsonObj.remove(k);
                jsonObj.put(k, fieldsValue);
                LOGGER.debug("Changing type of field -> " + k + " in the json to type -> String");
            }
        } catch(JSONException e) {
            LOGGER.error("Caught JSONException trying to change a JSON field type to String: " + e.getMessage());
            e.printStackTrace();
        }
        return jsonObj.toString();
    }
}
