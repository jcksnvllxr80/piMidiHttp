package com.watkins.http.handlers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

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
        try {
            JsonNode jsonNodeTree = new ObjectMapper().readTree(jsonString);
            return new YAMLMapper().writeValueAsString(jsonNodeTree);
        } catch (JsonProcessingException e) {
            logAndPrintStackTrace(e, LOGGER);
        }
        return null;
    }



    public String validatePathThenConvert(String filePath) {
        return isValidPath(filePath) ? convertYamlToJson(readFile(filePath)) : "Bad path: " + filePath;
    }
}
