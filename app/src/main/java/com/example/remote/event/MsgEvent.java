package com.example.remote.event;

import androidx.annotation.NonNull;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class MsgEvent implements Event {
    String msg;

    public MsgEvent(String msg) {
        this.msg = msg;
    }

    MsgEvent(String plain, boolean json) {
        JsonObject jsonObject = new JsonParser().parse(plain).getAsJsonObject();
        msg = jsonObject.get("msg").getAsString();
    }

    @NonNull
    @Override
    public String toString() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("name", "msg");
        jsonObject.addProperty("msg", msg);
        return jsonObject.toString();
    }
}
