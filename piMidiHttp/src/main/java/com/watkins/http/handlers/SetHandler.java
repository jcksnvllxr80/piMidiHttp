package com.watkins.http.handlers;

import org.json.JSONArray;
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


    public JSONArray getSetList() {
        LOGGER.debug("Get list of files (songs) at path. " + this.setlistsPath + ".");
        return csvStringToJsonArray(new File(this.setlistsPath).list());
    }


    public String getSetConfigFile(String setName) {
        return validatePathThenConvert(this.setlistsPath + setName);
    }


    public String createSetConfigFile(String setName, String setConfig) {
        String setConfigAsYaml = convertJsonToYaml(setConfig);
        writeToFile(this.setlistsPath + setName + ".yaml", setConfigAsYaml);
        LOGGER.info(setName + " setlist config was created as YAML\n" + setConfigAsYaml);
        return setConfigAsYaml;
    }
}
