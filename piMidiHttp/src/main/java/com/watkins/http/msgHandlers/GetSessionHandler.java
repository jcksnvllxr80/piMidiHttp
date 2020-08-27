package com.watkins.http.msgHandlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class GetSessionHandler extends MessagingHandlerUtilities {
    Logger LOGGER = LoggerFactory.getLogger(GetSessionHandler.class);
    private final ElasticUtilities elasticUtilities;

    public GetSessionHandler(int sessionId, ElasticUtilities elasticUtilities) {
        super(sessionId);
        this.elasticUtilities = elasticUtilities;
    }


    public String getGetSessionResponse(String message){

        return message;
    }


    public String createGetSessionRequestEMP(String message)  {

        return message;
    }
}
