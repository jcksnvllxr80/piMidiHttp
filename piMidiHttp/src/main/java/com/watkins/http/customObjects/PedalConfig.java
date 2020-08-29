package com.watkins.http.customObjects;

import java.util.ArrayList;

public class PedalConfig {

    private ArrayList<String> commandGroupList;

    PedalConfig() {}


    PedalConfig(ArrayList<String> commandGroupList) {
        this.commandGroupList = commandGroupList;
    }


    public ArrayList<String> getCommandGroupList() {
        return this.commandGroupList;
    }


    @Override
    public String toString() {
        return String.join(", ", this.commandGroupList);
    }
}
