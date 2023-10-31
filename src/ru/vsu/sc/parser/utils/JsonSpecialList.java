package ru.vsu.sc.parser.utils;

import java.util.ArrayList;

public class JsonSpecialList<V extends JsonObject> extends ArrayList<V> implements JsonObject{
    @Override
    public JsonObject byIndex(int i) {
        return super.get(i);
    }

    @Override
    public JsonObject byKey(String key) {
        return ((JsonSpecialMap<String, JsonObject>)super.get(0)).get(key);
    }
}
