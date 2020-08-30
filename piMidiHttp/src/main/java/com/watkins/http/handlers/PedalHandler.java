package com.watkins.http.handlers;

import org.json.JSONArray;
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


    public JSONArray getPedalList() {
        LOGGER.debug("Get list of files (pedals) at path. " + this.pedalsPath + ".");
        return csvStringToJsonArray(new File(this.pedalsPath).list());
    }


    public String getPedalConfigFile(String pedalName) {
        return validatePathThenConvert(this.pedalsPath + pedalName);
    }


    public String createPedalConfigFile(String pedalName, String pedalConfig) {
        String pedalConfigAsYaml = convertJsonToYaml(pedalConfig);
        writeToFile(this.pedalsPath + pedalName + ".yaml", pedalConfigAsYaml);
        LOGGER.info(pedalName + " pedal config as YAML\n" + pedalConfigAsYaml);
        return pedalConfigAsYaml;
    }
}
