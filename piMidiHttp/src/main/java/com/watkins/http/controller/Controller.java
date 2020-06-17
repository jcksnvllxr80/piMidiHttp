package com.watkins.http.controller;

import com.watkins.http.customObjects.SubscriptionList;
import com.watkins.http.messaging.LatestResponse;
import com.watkins.http.messaging.Producer;
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
    @Value("${sam.controller.response-wait-time-ms:2000}")
    private int responseWaitTimeMs;
    public final Logger LOGGER = LoggerFactory.getLogger(Controller.class);
    static final int TIMEOUT_MS = 100;
    private final String getSessionUsage =
        "\nGet Session -> \"curl -XPUT http://host:port/getSession\" USAGE:\n" +
        "\tUsage Example:\n" +
        "\t\tcurl -XPUT http://10.225.203.51:8081/getSession\n";
    private final String closeSessionUsage =
        "\nGet Session -> \"curl -XPUT http://host:port/closeSession/sessionId\" USAGE:\n" +
        "\tUsage Example:\n" +
        "\t\tcurl -XPUT http://10.225.203.51:8081/closeSession/5\n" +
        "\tNOTE: \'sessionId\' is the sessionId to be closed\n";
    private final String sendMessageWithArgsUsage =
        "\nSend Message With Args -> \"curl -XPUT http://host:port/sendMessageWithArgs/messageID/sessionId/<args>\" USAGE:\n" +
        "\t\'args\' can be any of following or multiple and must be separated by commas.\n" +
        "\t\t-s, --smid \t\tSystems Management ID\n" +
        "\t\t-e, --emp-address \tEMP address\n" +
        "\t\t-r, --rr-scac \tRailroad SCAC\n" +
        "\tUsage Examples:\n" +
        "\t\tcurl -XPUT http://10.225.203.51:8081/sendMessageWithArgs/10215/5/-s,abcd:1234\n" +
        "\t\tcurl -XPUT http://10.225.203.51:8081/sendMessageWithArgs/10215/5/-s=abcd:1234\n" +
        "\t\tcurl -XPUT http://10.225.203.51:8081/sendMessageWithArgs/10215/5/--smid,abcd:1234\n" +
        "\t\tcurl -XPUT http://10.225.203.51:8081/sendMessageWithArgs/10215/5/--smid=abcd:1234\n" +
        "\t\tcurl -XPUT http://10.225.203.51:8081/sendMessageWithArgs/10113/5/-s,abcd:1234,-e,abcd.b\n" +
        "\t\tcurl -XPUT http://10.225.203.51:8081/sendMessageWithArgs/10113/5/-s=abcd:1234,-e=abcd.b\n" +
        "\t\tcurl -XPUT http://10.225.203.51:8081/sendMessageWithArgs/10113/5/--smid,abcd:1234,--emp-address,abcd.b\n" +
        "\t\tcurl -XPUT http://10.225.203.51:8081/sendMessageWithArgs/10113/5/--smid=abcd:1234,--emp-address=abcd.b\n" +
        "\nHelp -> \"curl -XPUT http://host:port/help\" USAGE:\n" +
        "\tUsage:\n" +
        "\t\tcurl -XPUT http://10.225.203.51:8081/help\n";

    @Autowired
    private Producer producer;
    @Autowired
    private Parser parser;
    @Autowired
    private LatestResponse latestResponse;


    @PostMapping("/unsubscribe/{sessionId}")
    String unsubscribeEventStatus(@PathVariable String sessionId, @RequestBody SubscriptionList unsubscribeList) {
        String message = parser.createMsg(unsubscribeList.getSmidList());
        String loggingStr = "sent with message ID -> " + SETTINGS_ID + ", session ID -> "
                + sessionId + ", and list of subscriptions -> " +  unsubscribeList.getSmidList().toString();
        return getMessageResponse(checkAndSendMessageUsage(message, loggingStr));
    }


    @PutMapping(value = "/help", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    String printHelp() {
        String generalUsage = getSessionUsage + closeSessionUsage + sendMessageWithArgsUsage;
        return getHelp(generalUsage);
    }


    private String checkAndSendMessageUsage(String message, String loggingStr) {
        String loggingString;
        if (message == null) {
            loggingString = "Message NOT " + loggingStr;
            LOGGER.error(loggingString);
        } else {
            loggingString = "Message " + loggingStr;
            producer.sendEmpMessage(message);
            LOGGER.info(loggingString);
        }
        return loggingString + "\n";
    }
    

    public String getHelp(String usage) {
        String helpMessage = usage;
        LOGGER.info(helpMessage);
        return helpMessage;
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