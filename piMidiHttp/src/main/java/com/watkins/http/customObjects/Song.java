package com.watkins.http.customObjects;

import java.util.Map;
import java.util.stream.Collectors;

public class Song {
    private Map<Setlist, Part> empSmidPair;

    public Song() {
    }


    public Song(Map<Setlist, Part> empSmidPair) {
        this.empSmidPair = empSmidPair;
    }


    public void setEmpSmidPair(Map<Setlist, Part> empSmidPair) {
        this.empSmidPair = empSmidPair;
    }


    public Map<Setlist, Part> getEmpSmidPair() {
        return this.empSmidPair;
    }


    @Override
    public String toString() {
        return this.empSmidPair.entrySet().stream().map(pair -> "{SMID: " + pair.getKey() + ", EMP Address: " + pair.getValue() + "}").collect(Collectors.joining(" "));
    }
}
