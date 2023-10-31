package ru.vsu.sc.parser.utils;

import java.util.HashMap;

public class JsonSpecialMap<K, V extends JsonObject> extends HashMap<K, V> implements JsonObject {


    @Override
    public JsonObject byIndex(int i) {
        throw new IllegalArgumentException("(SpecialMap) Not possible get by index");
    }

    @Override
    public JsonObject byKey(String key) {
        return super.get(key);
    }



}
