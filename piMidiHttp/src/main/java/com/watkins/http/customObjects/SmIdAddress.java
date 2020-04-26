package com.watkins.http.customObjects;

public class SmIdAddress {
    private String smId;

    public SmIdAddress() {
    }


    public SmIdAddress(String empSmidPair) {
        this.smId = empSmidPair;
    }


    public void setSmId(String smId) {
        this.smId = smId;
    }


    public String getSmIdAddress() {
        return this.smId;
    }


    @Override
    public String toString() {
        return this.smId;
    }
}
