package com.watkins.http.handlers;

import com.watkins.http.config.HandlerConfig;
import org.json.JSONArray;
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
        isValidPath(this.midiControllerConfigPath);
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


    public JSONArray getPedals() {
        return new PedalHandler(this.pedalsPath).getPedalList();
    }


    public String getPedalConfig(String pedalName) {
        return new PedalHandler(this.pedalsPath).getPedalConfigFile(pedalName);
    }


    public String createPedalConfig(String pedalName, String pedalConfig) {
        return new PedalHandler(this.pedalsPath).createPedalConfigFile(pedalName, pedalConfig);
    }


    public JSONArray getSongs() {
        return new SongHandler(this.songsPath).getSongList();
    }


    public String getSongConfig(String songName) {
        return new SongHandler(this.songsPath).getSongConfigFile(songName);
    }


    public String createSongConfig(String songName, String songConfig) {
        return new SongHandler(this.songsPath).createSongConfigFile(songName, songConfig);
    }


    public JSONArray getSets() {
        return new SetHandler(this.setlistsPath).getSetList();
    }


    public String getSetConfig(String setName) {
        return new SetHandler(this.setlistsPath).getSetConfigFile(setName);
    }


    public String createSetConfig(String setName, String setConfig) {
        return new SetHandler(this.setlistsPath).createSetConfigFile(setName, setConfig);
    }
}
