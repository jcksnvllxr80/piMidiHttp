package com.watkins.http.controller;

import com.watkins.http.customObjects.SubscriptionList;
import com.watkins.http.messaging.LatestResponse;
import com.watkins.http.parser.Parser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

import static com.watkins.http.parser.MessageType.*;

@RestController("/controller")
public class Controller {
    @Value("${pi-midi-http.controller.response-wait-time-ms:2000}")
    private int responseWaitTimeMs;
    public final Logger LOGGER = LoggerFactory.getLogger(Controller.class);
    static final int TIMEOUT_MS = 100;

    @Autowired
    private Parser parser;
    @Autowired
    private LatestResponse latestResponse;

    @PostMapping("/unsubscribe/{sessionId}")
    String unsubscribeEventStatus(@PathVariable String sessionId, @RequestBody SubscriptionList unsubscribeList) {
        String message = parser.createMsg();
        String loggingStr = "sent with message ID -> " + SETTINGS_ID + ", session ID -> "
                + sessionId + ", and list of subscriptions -> " +  unsubscribeList.getSmidList().toString();
        return getMessageResponse(checkAndSendMessageUsage(message, loggingStr));
    }


    @PutMapping(value = "/help", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    String printHelp() {
        String usage = "This is a placeholder for the help string.";
        return getHelp(usage);
    }


    private String checkAndSendMessageUsage(String message, String loggingStr) {
        String loggingString;
        if (message == null) {
            loggingString = "Message NOT " + loggingStr;
            LOGGER.error(loggingString);
        } else {
            loggingString = "Message " + loggingStr;
            LOGGER.info(loggingString);
        }
        return loggingString + "\n";
    }
    

    public String getHelp(String usage) {
        LOGGER.info(usage);
        return usage;
    }


    public String getMessageResponse(String sendMessageOutput){
        try {
            waitForResponse();
        } catch (InterruptedException e) {
            LOGGER.error("Caught exception trying to sleep: " + e.getMessage());
            e.printStackTrace();
        }
        return latestResponse.getResponse() + sendMessageOutput;
    }


    public void waitForResponse() throws InterruptedException {
        for (int i = 0; !latestResponse.hasResponse(); i++){
            if (i*TIMEOUT_MS > responseWaitTimeMs){
                LOGGER.error("The response wait time has been exceeded: " + responseWaitTimeMs + "ms.");
                break;
            }
            LOGGER.debug("Sleep for " + TIMEOUT_MS + " while waiting for response.");
            TimeUnit.MILLISECONDS.sleep(TIMEOUT_MS);
        }
    }
}