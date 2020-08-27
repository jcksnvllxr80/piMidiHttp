package com.watkins.http.parser;

import com.watkins.http.config.ParserConfig;
import com.watkins.http.msgHandlers.*;
import com.watkins.http.messaging.LatestResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.File;
import java.util.ArrayList;

@Service
public class Parser {
    public final Logger LOGGER = LoggerFactory.getLogger(Parser.class);
    public final String keystorePath;
    public final String keystorePassword;
    public final ArrayList<String> statusPropList;
    public int sessionId;
    public int messageId;
    private final LatestResponse latestResponse;
    private final ElasticUtilities elasticUtilities;

    @Autowired
    public Parser(ParserConfig parserConfig, LatestResponse latestResponse1, ElasticUtilities elasticUtilities1) {
        this.keystorePath = parserConfig.getKeystorePath();
        this.keystorePassword = parserConfig.getKeystorePassword();
        this.statusPropList = parserConfig.getStatusPropertiesList();
        this.latestResponse = latestResponse1;
        this.elasticUtilities = elasticUtilities1;
        this.messageId = 10101;
        this.sessionId = 0;
        LOGGER.info("Created Parser with " +
                "\nkeystorePath: " + this.keystorePath +
                "\nkeystorePassword: " + this.keystorePassword +
                "\nstatusPropList: " + this.statusPropList +
                "\nmessageId: " + this.messageId +
                "\nsessionId: " + this.sessionId
        );
        File keystoreFile = new File(this.keystorePath);
        if (!keystoreFile.exists()) {
            LOGGER.error("Keystore file, " + keystorePath + " does not exist! Please configure this in the application.yml file.");
        }
    }
    

    public void handleMessage(String message) {
        String response;
        LOGGER.debug("message type was received.");

        GetSessionHandler getSessionHandler = new GetSessionHandler(this.sessionId, elasticUtilities);
        response = getSessionHandler.getGetSessionResponse(message);

        LOGGER.info("Received response: " + response + ".");
        latestResponse.setResponse(response + "\n");
    }

    public String createMsg() {
        return "hello";
    }

    public String getMessage() {
        return "hello";
    }
}
