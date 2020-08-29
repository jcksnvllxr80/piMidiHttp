package com.watkins.http.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@Component
@ConfigurationProperties(value="pi-midi-http.handler")
public class HandlerConfig {
    private String setlistsPath;
    private String pedalsPath;
    private String midiControllerConfigPath;

    public String getSetlistsPath() {
        return setlistsPath;
    }

    public void setSetlistsPath(String setlistsPath) {
        this.setlistsPath = setlistsPath;
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
