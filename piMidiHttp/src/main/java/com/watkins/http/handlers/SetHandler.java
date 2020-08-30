package com.watkins.http.handlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class SetHandler extends HandlerUtilities{
    Logger LOGGER = LoggerFactory.getLogger(SetHandler.class);
    String setlistsPath;

    public SetHandler(String setlistsPath) {
        this.setlistsPath = setlistsPath;
        LOGGER.debug("Instantiating " + this.getClass().getName() + " object. " + this.toString());
    }


    @Override
    public String toString() {
        return "SetHandler{" +
                "setlistsPath='" + setlistsPath + '\'' +
                '}';
    }

    
    public String[] getSetList() {
        LOGGER.debug("Get list of files (songs) at path. " + this.setlistsPath + ".");
        return new File(this.setlistsPath).list();
    }


    public String getSetConfigYaml(String setName) {
        return testUnderscoresThenSpaces(this.setlistsPath, setName);
    }
}
