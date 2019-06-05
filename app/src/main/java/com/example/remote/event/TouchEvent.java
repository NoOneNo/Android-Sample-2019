package com.example.remote.event;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class TouchEvent implements Event {
    public final int action;
    public final float x;
    public final float y;

    public TouchEvent(int action, float x, float y) {
        this.action = action;
        this.x = x;
        this.y = y;
    }

    TouchEvent(String plain) {
        JsonObject jsonObject = new JsonParser().parse(plain).getAsJsonObject();
        action = jsonObject.get("action").getAsInt();
        x = jsonObject.get("x").getAsFloat();
        y = jsonObject.get("y").getAsFloat();
    }

    public String toString() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("name", "touch");
        jsonObject.addProperty("action", action);
        jsonObject.addProperty("x", x);
        jsonObject.addProperty("y", y);
        return jsonObject.toString();
    }
}
