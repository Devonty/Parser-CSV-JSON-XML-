package ru.vsu.sc.parser.utils;

import ru.vsu.sc.parser.JSONParser;

public interface JsonObject{

    JsonObject byIndex(int i);
    JsonObject byKey(String  key);

    default Object open(){
        throw new RuntimeException("(JsonObject) Cannot be opened");
    }

}
