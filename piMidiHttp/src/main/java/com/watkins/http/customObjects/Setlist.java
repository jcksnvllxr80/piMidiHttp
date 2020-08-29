package com.watkins.http.customObjects;

public class Setlist {
    private String smId;

    public Setlist() {
    }


    public Setlist(String empSmidPair) {
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
