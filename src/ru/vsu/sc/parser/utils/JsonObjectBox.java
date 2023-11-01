package ru.vsu.sc.parser.utils;

public class JsonObjectBox implements JsonObject {
    Object object;

    public JsonObjectBox(Object object) {
        this.object = object;
    }

    public Object getObject() {
        return object;
    }

    @Override
    public Object open() {
        return getObject();
    }

    public void setObject(Object object) {
        this.object = object;
    }

    @Override
    public String toString() {
        return "JsonObjectBox{" +
                "object=" + object +
                '}';
    }

    @Override
    public JsonObject byIndex(int i) {
        throw new IllegalArgumentException("(JsonObjectBox) Not possible get by index");
    }

    @Override
    public JsonObject byKey(String key) {
        throw new IllegalArgumentException("(JsonObjectBox) Not possible get by key");

    }
}
