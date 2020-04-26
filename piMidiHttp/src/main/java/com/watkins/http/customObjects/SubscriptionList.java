package com.watkins.http.customObjects;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class SubscriptionList {
    private ArrayList<String> smidList;

    SubscriptionList() {}


    SubscriptionList(ArrayList<String> smidList) {
        this.smidList = smidList;
    }


    public ArrayList<String> getSmidList() {
        return this.smidList;
    }


    @Override
    public String toString() {
        return this.smidList.stream().collect(Collectors.joining(", "));
    }
}
