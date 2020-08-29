package com.watkins.http.customObjects;

import java.util.ArrayList;

public class SongList {
    private ArrayList<String> songList;


    public SongList() {
    }


    public SongList(ArrayList<String> statusPropertyList) {
        this.songList = statusPropertyList;
    }


    public ArrayList<String> getSongList() {
        return this.songList;
    }


    @Override
    public String toString() {
        return String.join(", ", this.songList);
    }
}
