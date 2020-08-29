package com.watkins.http.handlers;

import com.watkins.http.config.HandlerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.File;

@Service
public class Handler {
    public final Logger LOGGER = LoggerFactory.getLogger(Handler.class);
    public final String setlistsPath;
    public final String pedalsPath;
    public final String midiControllerConfigPath;

    @Autowired
    public Handler(HandlerConfig parserConfig) {
        this.setlistsPath = parserConfig.getSetlistsPath();
        this.pedalsPath = parserConfig.getPedalsPath();
        this.midiControllerConfigPath = parserConfig.getMidiControllerConfigPath();
        LOGGER.info("Created Parser with " +
                "\nsetlistsPath: " + this.setlistsPath +
                "\npedalsPath: " + this.pedalsPath +
                "\nmidiControllerConfigPath: " + this.midiControllerConfigPath
        );
        File midiControllerConfigFile = new File(this.midiControllerConfigPath);
        if (!midiControllerConfigFile.exists()) {
            LOGGER.error(String.join(", ", "MIDI controller file", this.midiControllerConfigPath,
                    "doesn't exist! Configure in the application.yml."));
        }
    }


    public String getPedals() {
        return "";
    }
}
