package com.watkins.http.messaging;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LatestResponse {
    private final Logger LOGGER = LoggerFactory.getLogger(LatestResponse.class);
    private String response;
    private Boolean responseFlag;

    public LatestResponse() {
        LOGGER.debug("A LatestResponse object has been created.");
        this.response = null;
        this.responseFlag = false;
    }


    public String getResponse() {
        String returnResponseVar = this.response;
        if (hasResponse()){
            this.response = null;
            this.responseFlag = false;
            LOGGER.debug("The latest message's response has been read and cleared from the response var: " + returnResponseVar);
        }
        else{
            LOGGER.warn("The latest message's response has had an attempted read but was null.");
            returnResponseVar = "There has been no response from the SMG. ";
        }
        return returnResponseVar;
    }


    public void setResponse(String response) {
        this.response = response;
        this.responseFlag = true;
        LOGGER.debug("The latest message's response has been received and stored in the response var: " + this.response);
    }


    public Boolean hasResponse() {
        return this.responseFlag;
    }
}
