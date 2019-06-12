package com.example.remote.event;

import android.util.Log;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class EventParser {
    public static Event parseEvent(String plain) {
        Log.i("debug", "" + plain);
        JsonObject jsonObject = new JsonParser().parse(plain).getAsJsonObject();
        String name = jsonObject.get("name").getAsString();

        if (name.equals("touch")) {
            return new TouchEvent(plain);
        }

        if (name.equals("msg")) {
            return new MsgEvent(plain, true);
        }

        return null;
    }
}
