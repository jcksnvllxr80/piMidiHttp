package com.watkins.http.handlers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.watkins.http.factories.YAMLFactory;
import net.minidev.json.JSONValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class HandlerUtilities {
    public final Logger LOGGER = LoggerFactory.getLogger(HandlerUtilities.class);


    public Boolean validatePath(String path) {
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


    public String replaceUnderscoreWithSpace(String filename) {
        char underscore = '_';
        char space = ' ';
        return filename.replace(underscore, space);
    }


    public void logAndPrintStackTrace(Exception e, Logger logger) {
        logger.error(e.getMessage());
        e.printStackTrace();
    }


//    public static String convertToJson(String yamlString) {
//        Yaml yaml= new Yaml();
//        Object obj = yaml.load(yamlString);
//
//        return JSONValue.toJSONString(obj);
//    }


    public String convertYamlToJson(String yaml) {
        ObjectMapper yamlReader = new ObjectMapper(new YAMLFactory());
        Object obj = null;
        try {
            obj = yamlReader.readValue(yaml, Object.class);
            ObjectMapper jsonWriter = new ObjectMapper();
            return jsonWriter.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }


    public String tryUnderscoresThenSpaces(String path, String filename) {
        String filePath = path + filename;
        String underscorelessFilePath = path + replaceUnderscoreWithSpace(filename);
        if (validatePath(filePath)) {
            return readFile(filePath);
        } else if (validatePath(underscorelessFilePath)) {
            return readFile(underscorelessFilePath);
        } else {
            return "Bad path: " + filePath;
        }
    }
}
