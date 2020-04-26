package com.watkins.http.messaging;

import com.watkins.http.parser.Parser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;

import javax.jms.*;

public class JmsConsumer implements AmqpInterface {
    private final Logger  LOGGER = LoggerFactory.getLogger(JmsConsumer.class);

    @Autowired
    private Parser parser;

    public JmsConsumer() {
        LOGGER.info("Created JmsConsumer.");
    }

    @JmsListener(containerFactory = "myJmsListenerContainerFactory", destination="${sam.jms.consumer-queue}")
    public void process(Message msg) {
        try {
            final BytesMessage tmp = (BytesMessage) msg;
            int len;
            len = (int) ((BytesMessage) msg).getBodyLength();
            byte[] recvMsg = new byte[len];
            tmp.readBytes(recvMsg);
            LOGGER.debug("The received message's body looks like:\n" + bytesToHex(recvMsg));
            parser.handleIsmpMessage(parser.getMessage());
        } catch (Exception exp) {
            LOGGER.warn("Caught exception. Message may not have been sent. ", exp.getCause());
            exp.printStackTrace(System.out);
//            System.exit(1);
        }
    }
}
