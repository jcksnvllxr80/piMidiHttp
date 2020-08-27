package com.watkins.http.customObjects;

import java.util.ArrayList;

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
        return String.join(", ", this.smidList);
    }
}
