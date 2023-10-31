package ru.vsu.sc.parser.utils;

public class JsonObjectBox implements JsonObject{
    Object object;

    public JsonObjectBox(Object object) {
        this.object = object;
    }

    public Object getObject() {
        return object;
    }

    public Object open(){
        return getObject();
    }

    public void setObject(Object object) {
        this.object = object;
    }

    @Override
    public String toString() {
        return "ObjectBox{" +
                "object=" + object +
                '}';
    }

    @Override
    public JsonObject byIndex(int i) {
        throw new IllegalArgumentException("(ObjectBox) Not possible get by index");
    }

    @Override
    public JsonObject byKey(String key) {
        throw new IllegalArgumentException("(ObjectBox) Not possible get by key");

    }
}
