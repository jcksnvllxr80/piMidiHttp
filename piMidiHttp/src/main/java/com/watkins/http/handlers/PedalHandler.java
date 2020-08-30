package com.watkins.http.handlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class PedalHandler extends HandlerUtilities {
    Logger LOGGER = LoggerFactory.getLogger(PedalHandler.class);
    String pedalsPath;

    public PedalHandler(String pedalsPath) {
        this.pedalsPath = pedalsPath;
        LOGGER.debug("Instantiating " + this.getClass().getName() + " object. " + this.toString());
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


    public String getPedalConfigYaml(String pedalName) {
        String filePath = this.pedalsPath + pedalName;
        return validatePath(filePath) ? readFile(filePath) : "Bad path: " + filePath;
    }
}