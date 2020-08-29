package com.watkins.http.handlers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;


public class PedalHandler extends HandlerUtilities {
    Logger LOGGER = LoggerFactory.getLogger(PedalHandler.class);
    String pedalsPath;

    public PedalHandler(String pedalsPath) {
        this.pedalsPath = pedalsPath;
        LOGGER.debug("Instantiating PedalHandler object. " + this.toString());
    }


    @Override
    public String toString() {
        return "PedalHandler{" +
                "pedalsPath='" + pedalsPath + '\'' +
                '}';
    }


    public String[] getPedalList() {
        LOGGER.debug("Get list of files (pedals) at path. " + this.pedalsPath + ".");
        return new File(this.pedalsPath).list();
    }


    public String getPedalConfigAsJson(String pedalName) {
        String fileString = null;
        try {
            fileString = readFile(this.pedalsPath + "/" + pedalName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileString;
    }
}
