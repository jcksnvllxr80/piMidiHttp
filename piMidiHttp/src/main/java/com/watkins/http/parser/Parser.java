package com.watkins.http.parser;

import com.watkins.http.customObjects.EmpSmidPair;
import com.watkins.http.ismpMsgHandlers.*;
import com.watkins.http.messaging.LatestResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.util.ArrayList;


import static com.watkins.http.parser.MessageType.*;

public class Parser {
    public final Logger LOGGER = LoggerFactory.getLogger(Parser.class);
    public final String keystorePath;
    public final String sourceEmpAddress;
    public final String keystorePassword;
    public final ArrayList<String> statusPropList;
    public final String username;
    public int sessionId;
    public int messageId;
    public final String destinationEmpAddress;
    public final String scac;

    @Autowired
    ElasticUtilities elasticUtilities;
    @Autowired
    private LatestResponse latestResponse;

    public Parser(String keystorePath, String sourceEmpAddress, String keystorePassword, ArrayList<String> statusPropList,
                  String username, String destinationEmpAddress, String scac) {
        this.keystorePath = keystorePath;
        this.sourceEmpAddress = sourceEmpAddress;
        this.keystorePassword = keystorePassword;
        this.statusPropList = statusPropList;
        this.username = username;
        this.messageId = 10101;
        this.sessionId = 0;
        this.destinationEmpAddress = destinationEmpAddress;
        this.scac = scac;
        LOGGER.info("Created Parser with " +
                "\nkeystorePath: " + this.keystorePath +
                "\nsourceEmpAddress: " + this.sourceEmpAddress +
                "\nkeystorePassword: " + this.keystorePassword +
                "\nstatusPropList: " + this.statusPropList +
                "\nusername: " + this.username +
                "\nmessageId: " + this.messageId +
                "\nsessionId: " + this.sessionId +
                "\ndestinationEmpAddress: " + this.destinationEmpAddress
        );
        File keystoreFile = new File(this.keystorePath);
        if (!keystoreFile.exists()) {
            LOGGER.error("Keystore file, " + keystorePath + " does not exist! Please configure this in the application.yml file.");
//            System.exit(1);
        }
    }
    

    public void handleIsmpMessage(String message) {
        String response = null;
        LOGGER.debug("message type was received.");

        GetSessionHandler getSessionHndlr = new GetSessionHandler(
                this.username, this.destinationEmpAddress, this.sessionId, this.sourceEmpAddress, elasticUtilities);
        response = getSessionHndlr.getGetSessionResponse(message);


        LOGGER.info("Received response: " + response + ".");
        latestResponse.setResponse(response + "\n");
    }

    public String createMsg(ArrayList<String> smidList) {
        return "hello";
    }

    public String getMessage() {
        return "hello";
    }
}
