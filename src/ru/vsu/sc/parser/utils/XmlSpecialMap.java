package ru.vsu.sc.parser.utils;

import java.util.HashMap;

public class XmlSpecialMap<K extends String, V extends XmlSpecialList<XmlObject>> extends HashMap<K, V> implements XmlObject {
    @Override
    public XmlObject byKey(String key) {
        XmlSpecialList<XmlObject> obj = super.get(key);
        if(obj.size() == 1) return obj.byIndex(0);
        return obj;
    }

    @Override
    public XmlObject byIndex(int i) {
        throw new RuntimeException("(XmlSpecialMap) Not possible to get by index");

    }
}
