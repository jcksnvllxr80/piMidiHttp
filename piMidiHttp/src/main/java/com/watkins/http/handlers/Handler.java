package com.watkins.http.handlers;

import com.watkins.http.config.HandlerConfig;
import com.watkins.http.customObjects.PedalConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Handler extends HandlerUtilities {
    public final Logger LOGGER = LoggerFactory.getLogger(Handler.class);
    public final String songSetPath;
    public final String songsPath;
    public final String setlistsPath;
    public final String pedalsPath;
    public final String midiControllerConfigPath;

    @Autowired
    public Handler(HandlerConfig handlerConfig) {
        this.songSetPath = handlerConfig.getSongSetPath();
        this.songsPath = this.songSetPath + "Songs/";
        this.setlistsPath = this.songSetPath + "Sets/";
        this.pedalsPath = handlerConfig.getPedalsPath();
        this.midiControllerConfigPath = handlerConfig.getMidiControllerConfigPath();
        LOGGER.debug("Instantiating Handler object. " + this.toString());
        validatePath(this.midiControllerConfigPath);
    }

    @Override
    public String toString() {
        return "Handler{" +
                "songSetPath='" + songSetPath + '\'' +
                ", songsPath='" + songsPath + '\'' +
                ", setlistsPath='" + setlistsPath + '\'' +
                ", pedalsPath='" + pedalsPath + '\'' +
                ", midiControllerConfigPath='" + midiControllerConfigPath + '\'' +
                '}';
    }


    public String[] getPedals() {
        return new PedalHandler(this.pedalsPath).getPedalList();
    }


    public String getPedalConfig(String pedalName) {
        return new PedalHandler(this.pedalsPath).getPedalConfigYaml(pedalName);
    }


    public String createPedalConfig(String pedalName, PedalConfig pedalConfig) {
        return "This is a stub for the createPedalConfig method.";
    }


    public String[] getSongs() {
        return new SongHandler(this.songsPath).getSongList();
    }


    public String getSongConfig(String songName) {
        return new SongHandler(this.songsPath).getSongConfigYaml(songName);
    }


    public String createSongConfig(String songName, PedalConfig songConfig) {
        return "This is a stub for the createSongConfig method.";
    }


    public String[] getSets() {
        return new SetHandler(this.setlistsPath).getSetList();
    }


    public String getSetConfig(String setName) {
        return new SetHandler(this.setlistsPath).getSetConfigYaml(setName);
    }


    public String createSetConfig(String setName, PedalConfig setConfig) {
        return "This is a stub for the createSetConfig method.";
    }
}
