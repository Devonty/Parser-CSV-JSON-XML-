package ru.vsu.sc.parser.utils;

public class XmlObjectBox implements XmlObject{
    private Object object;

    public XmlObjectBox(Object object) {
        this.object = object;
    }


    @Override
    public String toString() {
        return "XmlObjectBox{" +
                "object=" + object +
                '}';
    }

    @Override
    public Object open() {
        return object;
    }
}
