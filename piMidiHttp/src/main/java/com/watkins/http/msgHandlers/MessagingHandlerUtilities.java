package com.watkins.http.msgHandlers;

import org.apache.commons.cli.*;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class MessagingHandlerUtilities {
    Logger LOGGER = LoggerFactory.getLogger(MessagingHandlerUtilities.class);
    public int sessionId;

    public MessagingHandlerUtilities() {
    }


    public MessagingHandlerUtilities(int sessionId){
        this.sessionId = sessionId;
    }


    public String handleResponse(Short responseCode){
        LOGGER.debug("Received message's response description: " + responseCode);
        return String.valueOf(responseCode);
    }


    public void setMessageSettings(String message){
//        LOGGER.debug("EMP message DataIntegrityType is set to: " + message.getDataIntegrityType());
//        message.setMessageNumberTransactionId(transactionID); // any non-zero number
    }


    public String addMessageSpecFields(String ismpJson, Map<String, String> fields) {
        JSONObject jsonObj = null;
        try {
            jsonObj = new JSONObject(ismpJson);
            //add fields in map to the json
            for (String k : fields.keySet()){
                jsonObj.put(k, fields.get(k));
                LOGGER.debug("inserting field (key) -> " + k + " into the json. Value -> " + fields.get(k));
            }
        } catch(JSONException e) {
            LOGGER.error("Caught JSONException trying to update JSON fields: " + e.getMessage());
            e.printStackTrace();
        }
        return jsonObj.toString();
    }


    public CommandLine validateArgs(String[] commandLineArgs) throws ParseException {
        Options options = new Options();
        options.addOption("s", "smid", true, "Systems Management ID");
        options.addOption("e", "emp-address", true, "EMP address");
        options.addOption("r", "rr-scac", true, "Railroad SCAC");
        options.addOption("d", "destination", true, "Destination Asset");
        options.addOption("i", "role-id", true, "Role ID");
        options.addOption("l", "access-level", true, "Access Level");
//        HelpFormatter formatter = new HelpFormatter();
        CommandLineParser parser = new DefaultParser();
        return parser.parse(options, commandLineArgs);
    }
}


