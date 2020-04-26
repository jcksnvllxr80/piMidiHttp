package com.watkins.http.parser;

import java.util.HashMap;
import java.util.Map;

public interface MessageType {
    int SETLIST_ID = 10000;
    int SONG_ID = 10001;
    int SETTINGS_ID = 10002;

    Map<Integer, String> messsageTypeMap = new HashMap<Integer, String>() {{
        put(10000, "setlist");
        put(10001, "song");
        put(10002, "settings");
    }};
}