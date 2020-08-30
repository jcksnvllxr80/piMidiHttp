package com.watkins.http.controller;

import com.watkins.http.handlers.Handler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

//import org.springframework.beans.factory.annotation.Value;

//import java.util.concurrent.TimeUnit;


@RestController("/controller")
public class Controller {
//    @Value("${pi-midi-http.controller.response-wait-time-ms:2000}")
//    private int responseWaitTimeMs;
    public final Logger LOGGER = LoggerFactory.getLogger(Controller.class);

    @Autowired
    private Handler handler;


    @PostMapping("/pedal/{pedalName}")
    String createPedalConfigFile(@PathVariable String pedalName, @RequestBody String pedalConfig) {
        String message = handler.createPedalConfig(pedalName, pedalConfig);
        String loggingStr = "Wrote config file for " + pedalName + ". -> \n" + pedalConfig.toString();
        return checkAndSendMessageUsage(message, loggingStr);
    }


    @GetMapping("/pedals/")
    @PutMapping("/pedals/")
    String getPedalList() {
        String message = String.join(", ", handler.getPedals());
        return checkAndSendMessageUsage(message, "list of pedals.");
    }


    @GetMapping("/pedal/{pedalName}")
    @PutMapping("/pedal/{pedalName}")
    String getPedalConfig(@PathVariable String pedalName) {
        String message = handler.getPedalConfig(pedalName);
        return checkAndSendMessageUsage(message, pedalName + "'s config file as a JSON String.");
    }


    @PostMapping("/song/{songName}")
    String createSongConfigFile(@PathVariable String songName, @RequestBody String songConfig) {
        String message = handler.createSongConfig(songName, songConfig);
        String loggingStr = "Wrote config file for " + songName + ". -> \n" + songConfig.toString();
        return checkAndSendMessageUsage(message, loggingStr);
    }


    @GetMapping("/songs/")
    @PutMapping("/songs/")
    String getSongList() {
        String message = String.join(", ", handler.getSongs());
        return checkAndSendMessageUsage(message, "list of songs.");
    }

    @GetMapping("/song/{songName}")
    @PutMapping("/song/{songName}")
    String getSongConfig(@PathVariable String songName) {
        String message = handler.getSongConfig(songName);
        return checkAndSendMessageUsage(message, songName + "'s config file as a JSON String.");
    }


    @PostMapping("/set/{setName}")
    String createSetConfigFile(@PathVariable String setName, @RequestBody String setConfig) {
        String message = handler.createSetConfig(setName, setConfig);
        String loggingStr = "Wrote config file for " + setName + ". -> \n" + setConfig.toString();
        return checkAndSendMessageUsage(message, loggingStr);
    }


    @GetMapping("/sets/")
    @PutMapping("/sets/")
    String getSetList() {
        String message = String.join(", ", handler.getSets());
        return checkAndSendMessageUsage(message, "list of sets.");
    }


    @GetMapping("/set/{setName}")
    @PutMapping("/set/{setName}")
    String getSetConfig(@PathVariable String setName) {
        String message = handler.getSetConfig(setName);
        return checkAndSendMessageUsage(message, setName + "'s config file as a JSON String.");
    }


    @GetMapping(value = "/help", produces = MediaType.APPLICATION_JSON_VALUE)
    @PutMapping(value = "/help", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    String printHelp() {
        String usage =
                "@PostMapping(\"/pedal/{pedalName}\") # sets ${pedalName}'s midi config\n" +
                "@PutMapping(\"/pedals/\") # gets the full list of midi pedals\n" +
                "@PutMapping(\"/pedal/{pedalName}\") # gets ${pedalName}'s midi config\n" +
                "@PostMapping(\"/song/{songName}\") # sets ${songName}'s config\n" +
                "@PutMapping(\"/songs/\") # gets the full list of songs\n" +
                "@PutMapping(\"/song/{songName}\") # gets ${songName}'s config\n" +
                "@PostMapping(\"/set/{setName}\") # sets ${setName}'s config\n" +
                "@PutMapping(\"/sets/\") # gets the full list of sets\n" +
                "@PutMapping(\"/set/{setName}\") # gets ${setName}'s config\n";
        return getHelp(usage);
    }


    private String checkAndSendMessageUsage(String message, String loggingStr) {
        String loggingString;
        if (message == null) {
            loggingString = "Not sending " + loggingStr;
            LOGGER.error(loggingString);
        } else {
            loggingString = "Sending " + loggingStr + "\n" + message;
            LOGGER.info(loggingString);
        }
        return loggingString + "\n";
    }
    

    public String getHelp(String usage) {
        LOGGER.info(usage);
        return usage;
    }
}