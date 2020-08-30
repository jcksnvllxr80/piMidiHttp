package com.watkins.http.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.watkins.http.handlers.Handler;
import net.minidev.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    @ResponseBody
    public String createPedalConfigFile(@PathVariable String pedalName, @RequestBody String pedalConfig) {
        String message = handler.createPedalConfig(pedalName, pedalConfig);
        String loggingStr = "Wrote config file for " + pedalName + ". -> \n" + pedalConfig.toString();
        return logResponse(message, loggingStr);
    }


    @GetMapping("/pedals")
    @ResponseBody
    public String getPedalList() {
        String message = makeJsonStringFromKeyPair("Pedals", handler.getPedals());
        return logResponse(message, "list of pedals.");
    }


    @GetMapping("/pedal/{pedalName}")
    @ResponseBody
    public String getPedalConfig(@PathVariable String pedalName) {
        String message = handler.getPedalConfig(pedalName);
        return logResponse(message, pedalName + "'s config file as a JSON String.");
    }


    @PostMapping("/song/{songName}")
    @ResponseBody
    public String createSongConfigFile(@PathVariable String songName, @RequestBody String songConfig) {
        String message = handler.createSongConfig(songName, songConfig);
        String loggingStr = "Wrote config file for " + songName + ". -> \n" + songConfig.toString();
        return logResponse(message, loggingStr);
    }


    @GetMapping("/songs")
    @ResponseBody
    public String getSongList() {
        String message = makeJsonStringFromKeyPair("Songs", handler.getSongs());
        return logResponse(message, "list of songs.");
    }


    @GetMapping("/song/{songName}")
    @ResponseBody
    public String getSongConfig(@PathVariable String songName) {
        String message = handler.getSongConfig(songName);
        return logResponse(message, songName + "'s config file as a JSON String.");
    }


    @PostMapping("/set/{setName}")
    @ResponseBody
    public String createSetConfigFile(@PathVariable String setName, @RequestBody String setConfig) {
        String message = handler.createSetConfig(setName, setConfig);
        String loggingStr = "Wrote config file for " + setName + ". -> \n" + setConfig.toString();
        return logResponse(message, loggingStr);
    }


    @GetMapping("/sets")
    @ResponseBody
    public String getSetList() {
        String message = makeJsonStringFromKeyPair("Sets", handler.getSets());
        return logResponse(message, "list of sets.");
    }


    @GetMapping("/set/{setName}")
    @ResponseBody
    public String getSetConfig(@PathVariable String setName) {
        String message = handler.getSetConfig(setName);
        return logResponse(message, setName + "'s config file as a JSON String.");
    }


    @GetMapping(value = "/help", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String printHelp() {
        return String.join("\n", "You asked for help so here it is!!", getHelp());
    }


    @RequestMapping(value = "*", method = { RequestMethod.GET, RequestMethod.PUT , RequestMethod.POST })
    @ResponseBody
    public String allFallback() {
        return String.join("\n", "Requests should be from these options.", getHelp());
    }


    private String logResponse(String message, String loggingStr) {
        String loggingString;
        if (message == null) {
            loggingString = "Not sending " + loggingStr;
            LOGGER.error(loggingString);
        } else {
            loggingString = "Sending " + loggingStr + "\n" + message;
            LOGGER.info(loggingString);
        }
        return message;
    }
    

    public String getHelp() {
        String usage =
                "@GetMapping(\"/help\")  # show the full list of API options\n" +
                "\nP E D A L\n" +
                "@PostMapping(\"/pedal/{pedalName}\")  # sets ${pedalName}'s midi config\n" +
                "@GetMapping(\"/pedals\")  # gets the full list of midi pedals\n" +
                "@GetMapping(\"/pedal/{pedalName}\")  # gets ${pedalName}'s midi config\n" +
                "\nS O N G\n" +
                "@PostMapping(\"/song/{songName}\")  # sets ${songName}'s config\n" +
                "@GetMapping(\"/songs\")  # gets the full list of songs\n" +
                "@GetMapping(\"/song/{songName}\")  # gets ${songName}'s config\n" +
                "\nS E T L I S T\n" +
                "@PostMapping(\"/set/{setName}\")  # sets ${setName}'s config\n" +
                "@GetMapping(\"/sets\")  # gets the full list of sets\n" +
                "@GetMapping(\"/set/{setName}\")  # gets ${setName}'s config\n";
        LOGGER.info(usage);
        return usage;
    }


    private String makeJsonStringFromKeyPair(String key, List<String> list) {
        return new JSONObject(){{
            put(key, new Gson().toJson(list));
        }}.toString();
    }
}