package com.watkins.http.customObjects;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class StatusPropertyList {
    private ArrayList<String> statusPropertyList;


    public StatusPropertyList() {
    }


    public StatusPropertyList(ArrayList<String> statusPropertyList) {
        this.statusPropertyList = statusPropertyList;
    }


    public ArrayList<String> getStatusPropertyList() {
        return this.statusPropertyList;
    }


    @Override
    public String toString() {
        return String.join(", ", this.statusPropertyList);
    }
}
