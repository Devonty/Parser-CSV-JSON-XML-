package ru.vsu.sc.parser.utils;

import java.util.LinkedList;

public class XmlSpecialList<V extends XmlObject> extends LinkedList<V> implements XmlObject {
    @Override
    public XmlObject byIndex(int i) {
        return super.get(i);
    }

    @Override
    public XmlObject byKey(String key) {
        if(size() == 1) return super.get(0).byKey(key);
        throw new RuntimeException("(XmlSpecialList) Not possible to get by key");
    }

    @Override
    public String toString() {
        if (size() == 1) return get(0).toString();
        return super.toString();
    }
}
