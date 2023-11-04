package ru.vsu.sc.parser.utils;

public interface XmlObject {
    default XmlObject byIndex(int i){
        throw new RuntimeException("(XmlObject) Not possible to get by index");
    }
    default XmlObject byKey(String key){
        throw new RuntimeException("(XmlObject) Not possible to get by key");
    }

    default Object open(){
        throw new RuntimeException("(XmlObject) Not possible to open");
    }
}

