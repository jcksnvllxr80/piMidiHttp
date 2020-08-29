package com.watkins.http.customObjects;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class PedalOption {
    private ArrayList<Song> assetList;

    public PedalOption() {

    }


    public PedalOption(ArrayList<Song> assetList) {
        this.assetList = assetList;
    }


    public void setAssetList(ArrayList<Song> assetList) {
        this.assetList = assetList;
    }


    public ArrayList<Song> getAssetList() {
        return this.assetList;
    }


    @Override
    public String toString() {
        return this.assetList.stream().map(Song::toString).collect(Collectors.joining(", "));
    }
}