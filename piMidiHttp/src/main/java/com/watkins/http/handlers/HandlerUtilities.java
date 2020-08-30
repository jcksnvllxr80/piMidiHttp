package com.watkins.http.handlers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

public class HandlerUtilities {
    public final Logger LOGGER = LoggerFactory.getLogger(HandlerUtilities.class);


    public Boolean isValidPath(String path) {
        boolean isValidPath = true;
        File midiControllerConfigFile = new File(path);
        if (!midiControllerConfigFile.exists()) {
            LOGGER.error("Path, " + path + ", doesn't exist! Please properly configure.");
            isValidPath = false;
        }
        return isValidPath;
    }


    public String readFile(String path) {
        LOGGER.debug("Reading file , " + path + ".");
        byte[] encoded = new byte[0];
        try {
            encoded = Files.readAllBytes(Paths.get(path));
        } catch (IOException e) {
            logAndPrintStackTrace(e, LOGGER);
        }
        return new String(encoded, StandardCharsets.US_ASCII);
    }


    public void logAndPrintStackTrace(Exception e, Logger logger) {
        logger.error(e.getMessage());
        e.printStackTrace();
    }


    public String convertYamlToJson(String yaml) {
        LOGGER.debug("Attempting to convert Yaml to Json.");
        try {
            ObjectMapper yamlReader = new ObjectMapper(new YAMLFactory());
            Object obj = yamlReader.readValue(yaml, Object.class);
            ObjectMapper jsonWriter = new ObjectMapper();
            return jsonWriter.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
        } catch (IOException e) {
            logAndPrintStackTrace(e, LOGGER);
        }
        return null;
    }


    public String convertJsonToYaml(String jsonString) {
        LOGGER.debug("Attempting to convert Json to Yaml.");
        try {
            JsonNode jsonNodeTree = new ObjectMapper().readTree(jsonString);
            return new YAMLMapper().writeValueAsString(jsonNodeTree);
        } catch (JsonProcessingException e) {
            logAndPrintStackTrace(e, LOGGER);
        }
        return null;
    }


    public void writeToFile(String filepath, String content) {
        LOGGER.debug("Attempting to write content to file, " + filepath + ".");
        try {
            if (new File(filepath).exists()) {
                // TODO: overwrite files in the future
                LOGGER.warn(filepath + " already exists. Currently not overwriting files.");
            } else {
                BufferedWriter writer = new BufferedWriter(new FileWriter(filepath));
                writer.write(content);
                writer.close();
            }
        } catch (IOException e) {
            logAndPrintStackTrace(e, LOGGER);
        }
    }


    public JSONArray csvStringToJsonArray(String[] csvString){
        JSONArray jsonArray = new JSONArray();
        Arrays.asList(Arrays.toString(csvString).split(", "))
                .forEach(item -> jsonArray.put(item.replaceAll("[\\[\\]]", "")));
        return jsonArray;
    }


    public String validatePathThenConvert(String filePath) {
        return isValidPath(filePath) ? convertYamlToJson(readFile(filePath)) : "Bad path: " + filePath;
    }
}
