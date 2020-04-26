package com.watkins.http.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(value="sam")
public class SamConfig {
    private String sourceEmpAddress;
    private String username;

    public String getSourceEmpAddress() {
        return sourceEmpAddress;
    }

    public void setSourceEmpAddress(String sourceEmpAddress) {
        this.sourceEmpAddress = sourceEmpAddress;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
