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
    public final String setlistsPath;
    public final String pedalsPath;
    public final String midiControllerConfigPath;

    @Autowired
    public Handler(HandlerConfig handlerConfig) {
        this.setlistsPath = handlerConfig.getSetlistsPath();
        this.pedalsPath = handlerConfig.getPedalsPath();
        this.midiControllerConfigPath = handlerConfig.getMidiControllerConfigPath();
        LOGGER.debug("Instantiating Handler object. " + this.toString());
        validatePath(this.midiControllerConfigPath);
    }


    @Override
    public String toString() {
        return "Handler{" +
                "setlistsPath='" + setlistsPath + '\'' +
                ", pedalsPath='" + pedalsPath + '\'' +
                ", midiControllerConfigPath='" + midiControllerConfigPath + '\'' +
                '}';
    }


    public String[] getPedals() {
        return new PedalHandler(this.pedalsPath).getPedalList();
    }


    public String createPedalConfig(String pedalName, PedalConfig pedalConfig) {
        return "This is a stub for the createPedalConfig method.";
    }


    public String getPedalConfig(String pedalName) {
        return new PedalHandler(this.pedalsPath).getPedalConfigJson(pedalName);
    }
}
