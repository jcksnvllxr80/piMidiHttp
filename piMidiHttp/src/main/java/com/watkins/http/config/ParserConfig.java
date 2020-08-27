package com.watkins.http.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
@ConfigurationProperties(value="pi-midi-http.parser")
public class ParserConfig {
    private String keystorePath;
    private String keystorePassword;
    private ArrayList<String> statusPropertiesList;

    public ArrayList<String> getStatusPropertiesList() {
        return statusPropertiesList;
    }

    public void setStatusPropertiesList(ArrayList<String> statusPropertiesList) {
        this.statusPropertiesList = statusPropertiesList;
    }

    public String getKeystorePath() {
        return keystorePath;
    }

    public void setKeystorePath(String keystorePath) {
        this.keystorePath = keystorePath;
    }

    public String getKeystorePassword() {
        return keystorePassword;
    }

    public void setKeystorePassword(String keystorePassword) {
        this.keystorePassword = keystorePassword;
    }
}
