package com.watkins.http.customObjects;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class PartList {
    private ArrayList<String> statusPropertyList;


    public PartList() {
    }


    public PartList(ArrayList<String> statusPropertyList) {
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
