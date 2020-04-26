package com.watkins.http.customObjects;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class AssetList {
    private ArrayList<EmpSmidPair> assetList;

    public AssetList() {

    }


    public AssetList(ArrayList<EmpSmidPair> assetList) {
        this.assetList = assetList;
    }


    public void setAssetList(ArrayList<EmpSmidPair> assetList) {
        this.assetList = assetList;
    }


    public ArrayList<EmpSmidPair> getAssetList() {
        return this.assetList;
    }


    @Override
    public String toString() {
        return this.assetList.stream().map(EmpSmidPair::toString).collect(Collectors.joining(", "));
    }
}