package com.example.remote.event;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class EventParser {
    public static Event parseEvent(String plain) {
        JsonObject jsonObject = new JsonParser().parse(plain).getAsJsonObject();
        String name = jsonObject.get("name").getAsString();

        if (name.equals("touch")) {
            return new TouchEvent(plain);
        }

        return null;
    }
}
