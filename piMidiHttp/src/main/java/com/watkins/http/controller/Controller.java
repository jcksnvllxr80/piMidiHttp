package com.watkins.http.controller;

import com.watkins.http.customObjects.PedalConfig;
import com.watkins.http.handlers.Handler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.stream.Collectors;

//import java.util.concurrent.TimeUnit;


@RestController("/controller")
public class Controller {
//    @Value("${pi-midi-http.controller.response-wait-time-ms:2000}")
//    private int responseWaitTimeMs;
    public final Logger LOGGER = LoggerFactory.getLogger(Controller.class);
    static final int TIMEOUT_MS = 100;

    @Autowired
    private Handler handler;

    @PostMapping("/pedal/{pedalName}")
    String createPedalConfigFile(@PathVariable String pedalName, @RequestBody PedalConfig pedalConfig) {
        String message = handler.createPedalConfig(pedalName, pedalConfig);
        String loggingStr = "Wrote config file for " + pedalName + ". -> \n" +
                pedalConfig.toString();
        return checkAndSendMessageUsage(message, loggingStr);
    }


    @PutMapping("/pedals/")
    String getPedalsList() {
        String message = String.join(", ", handler.getPedals());
        return checkAndSendMessageUsage(message, "list of pedals.");
    }


    @PutMapping("/pedal/{pedalName}")
    String getPedalsList(@PathVariable String pedalName) {
        String message = handler.getPedalConfig(pedalName);
        return checkAndSendMessageUsage(message, pedalName + "'s config file as a json object.");
    }


    @PutMapping(value = "/help", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    String printHelp() {
        String usage = "This is a placeholder for the help string. ";
        return getHelp(usage);
    }


    private String checkAndSendMessageUsage(String message, String loggingStr) {
        String loggingString;
        if (message == null) {
            loggingString = "Not sending " + loggingStr;
            LOGGER.error(loggingString);
        } else {
            loggingString = "Sending " + loggingStr + " " + message;
            LOGGER.info(loggingString + " " + message);
        }
        return loggingString + "\n";
    }
    

    public String getHelp(String usage) {
        LOGGER.info(usage);
        return usage;
    }
}