package com.watkins.http.ismpMsgHandlers;

import com.watkins.http.customObjects.EmpAddress;
import com.watkins.http.customObjects.EmpSmidPair;
import com.watkins.http.customObjects.SmIdAddress;
import org.apache.commons.cli.*;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MessagingHandlerUtilities {
    Logger LOGGER = LoggerFactory.getLogger(MessagingHandlerUtilities.class);
    int MESSAGE_VERSION = 1;
    public String username;
    public int sessionId;
    public String sourceEmpAddress;
    public String destinationEmpAddress;

    public MessagingHandlerUtilities() {
    }

    public MessagingHandlerUtilities(String username, String destinationEmpAddress, int sessionId, String sourceEmpAddress){
        this.username = username;
        this.sessionId = sessionId;
        this.sourceEmpAddress = sourceEmpAddress;
        this.destinationEmpAddress = destinationEmpAddress;
    }


    public String handleResponse(Short responseCode){
        LOGGER.debug("Received message's response description: " + responseCode);
        return String.valueOf(responseCode);
    }


    public void setMessageSettings(String message){
        // populate the EMP header information
//        message.setDataIntegrityType(dataInteg);
//        LOGGER.debug("EMP message DataIntegrityType is set to: " + message.getDataIntegrityType());
//        message.setMessageNumberTransactionId(transactionID); // any non-zero number
//        LOGGER.debug("EMP message MessageNumberTransactionId is set to: " + message.getMessageNumberTransactionId());
//        message.setMessageNumberSessionId(this.sessionId); // zero for requesting session & non-zero for every other message
//        LOGGER.debug("EMP message MessageNumberSessionId is set to: " + message.getMessageNumberSessionId());
//        message.setProtocolVersion((short) 4);
//        LOGGER.debug("EMP message ProtocolVersion is set to: " + message.getProtocolVersion());
//        message.setBodyCompressed(false);
//        LOGGER.debug("EMP message BodyCompressed is set to: " + message.isBodyCompressed());
//        message.setBodyEncrypted(false);
//        LOGGER.debug("EMP message BodyEncrypted is set to: " + message.isBodyEncrypted());
//        message.setTimestampAbsolute(false);
//        LOGGER.debug("EMP message TimestampAbsolute is set to: " + message.isTimestampAbsolute());
//        message.setTimeToLive(5000);
//        LOGGER.debug("EMP message TimeToLive is set to: " + message.getTimeToLive());
//        // Routing QoS Fields
//        message.setQosClass(1);
//        LOGGER.debug("EMP message QosClass is set to: " + message.getQosClass());
//        message.setQosPriority(0);
//        LOGGER.debug("EMP message QosPriority is set to: " + message.getQosPriority());
//        message.setNetworkPreference(0);
//        LOGGER.debug("EMP message NetworkPreference is set to: " + message.getNetworkPreference());
//        message.setSpecialHandling(1);
//        LOGGER.debug("EMP message SpecialHandling is set to: " + message.getSpecialHandling());
//        message.setQosOutcomeNtfnRequested(true);
//        LOGGER.debug("EMP message QosOutcomeNtfnRequested is set to: " + message.isQosOutcomeNtfnRequested());
//        message.setQosDeliveryNtfnRequested(false);
//        LOGGER.debug("EMP message QosDeliveryNtfnRequested is set to: " + message.isQosDeliveryNtfnRequested());
//        message.setQosNtwkCompRequested(true);
//        LOGGER.debug("EMP message QosNtwkCompRequested is set to: " + message.isQosNtwkCompRequested());
//        message.setMessageTime(Instant.now().getEpochSecond());
//        LOGGER.debug("EMP message MessageTime is set to: " + message.getMessageTime());
//        message.setSourceAddress(this.sourceEmpAddress);
//        LOGGER.debug("EMP message SourceAddress is set to: " + message.getSourceAddress());
//        message.setDestAddress(this.destinationEmpAddress);
//        LOGGER.debug("EMP message DestAddress is set to: " + message.getDestAddress());
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


