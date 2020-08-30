package com.watkins.http.handlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class SongHandler extends HandlerUtilities {
    Logger LOGGER = LoggerFactory.getLogger(SongHandler.class);
    String songsPath;

    public SongHandler(String songsPath) {
        this.songsPath = songsPath;
        LOGGER.debug("Instantiating " + this.getClass().getName() + " object. " + this.toString());
    }


    @Override
    public String toString() {
        return "SongHandler{" +
                "songsPath='" + songsPath + '\'' +
                '}';
    }


    public String[] getSongList() {
        LOGGER.debug("Get list of files (songs) at path. " + this.songsPath + ".");
        return new File(this.songsPath).list();
    }


    public String getSongConfigFile(String songName) {
        return validatePathThenConvert(this.songsPath + songName);
    }


    public String createSongConfigFile(String songName, String songConfig) {
        String yamlOutput = convertJsonToYaml(songConfig);
        LOGGER.info(songName + " song config was created as YAML\n" + yamlOutput);
        return yamlOutput;
    }
}
