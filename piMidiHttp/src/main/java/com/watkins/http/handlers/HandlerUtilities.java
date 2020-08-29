package com.watkins.http.handlers;

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


    public String readFile(String path) {
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
}
