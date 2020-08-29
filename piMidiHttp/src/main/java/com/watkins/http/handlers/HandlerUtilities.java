package com.watkins.http.handlers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.watkins.http.factories.YAMLFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class HandlerUtilities {
    public final Logger LOGGER = LoggerFactory.getLogger(HandlerUtilities.class);

    public void validatePath(String path) {
        File midiControllerConfigFile = new File(path);
        if (!midiControllerConfigFile.exists()) {
            LOGGER.error("Path, " + path + ", doesn't exist! Please properly configure.");
        }
    }


    public String readFile(String path) throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, StandardCharsets.US_ASCII);
    }


    String convertYamlToJson(String yaml) throws JsonProcessingException {
        ObjectMapper yamlReader = new ObjectMapper(new YAMLFactory());
        Object obj = yamlReader.readValue(yaml, Object.class);

        ObjectMapper jsonWriter = new ObjectMapper();
        return jsonWriter.writeValueAsString(obj);
    }
}
