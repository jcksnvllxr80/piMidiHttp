package com.watkins.http.msgHandlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class GetSessionHandler extends MessagingHandlerUtilities {
    Logger LOGGER = LoggerFactory.getLogger(GetSessionHandler.class);
    private ElasticUtilities elasticUtilities;

    public GetSessionHandler(String username, String destinationEmpAddress, int sessionId, String sourceEmpAddress,
            ElasticUtilities elasticUtilities) {
        super(username, destinationEmpAddress, sessionId, sourceEmpAddress);
        this.elasticUtilities = elasticUtilities;
    }


    public String getGetSessionResponse(String message){

        return message;
    }


    public String createGetSessionRequestEMP(String message)  {

        return message;
    }
}
