package com.example.ws.event;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class EventParser {
    Event parseEvent(String plain) {
        JsonObject jsonObject = new JsonParser().parse(plain).getAsJsonObject();
        String name = jsonObject.get("name").getAsString();

        if (name.equals("touch")) {
            return new TouchEvent(plain);
        }

        return null;
    }
}
