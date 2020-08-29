package com.watkins.http.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@Component
@ConfigurationProperties(value="pi-midi-http.handler")
public class HandlerConfig {
    private String songSetPath;
    private String pedalsPath;
    private String midiControllerConfigPath;

    public String getSongSetPath() {
        return songSetPath;
    }

    public void setSongSetPath(String songSetPath) {
        this.songSetPath = songSetPath;
    }

    public String getPedalsPath() {
        return pedalsPath;
    }

    public void setPedalsPath(String pedalsPath) {
        this.pedalsPath = pedalsPath;
    }

    public String getMidiControllerConfigPath() {
        return midiControllerConfigPath;
    }

    public void setMidiControllerConfigPath(String midiControllerConfigPath) {
        this.midiControllerConfigPath = midiControllerConfigPath;
    }
}
