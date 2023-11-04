package ru.vsu.sc.parser;

import ru.vsu.sc.parser.utils.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class XMLParser implements XmlObject{
    private final XmlObject map;
    public XMLParser(String filePath) {
        this.map = parseXML(filePath);
    }


    @Override
    public XmlObject byIndex(int i) {
        return map.byIndex(i);
    }

    @Override
    public XmlObject byKey(String key) {
        return map.byKey(key);
    }

    private static String readFile(String filePath) {
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content.toString();
    }

    private static XmlObject parseXML(String filePath) {
        String xmlData = readFile(filePath);
        return parseXMLbyData(xmlData);
    }

    private static XmlObject parseXMLbyData(String xmlData) {
        return parseXML(new IndexWrapper(xmlData));
    }

    private static String parseTag(IndexWrapper iw) {
        assert iw.charNow() == '<' : "Не нечало тега";
        int indexStart = iw.getIndex();
        iw.nextWhileNot('>');
        return iw.getData().substring(indexStart + 1, iw.getIndex());
    }

    private static XmlObject parseXML(IndexWrapper iw) {
        Stack<String> tagStack = new Stack<>();
        Stack<Integer> indexStack = new Stack<>();
        Stack<XmlSpecialMap<String, XmlSpecialList<XmlObject>>> mapStack = new Stack<>();


        XmlSpecialMap<String, XmlSpecialList<XmlObject>> toReturn = new XmlSpecialMap<>();

        iw.nextWhileNot('<');
        String curTag = parseTag(iw);

        indexStack.add(iw.getIndex());
        tagStack.add("/" + curTag);
        mapStack.add(toReturn);
        mapStack.add(new XmlSpecialMap<>());

        //System.out.println(curTag);

        //System.out.println("Старт");
        //System.out.println(tagStack);

        while (!tagStack.isEmpty()) {
            iw.nextWhileNot('<');

            curTag = parseTag(iw);
            //System.out.println(curTag);

            if (Objects.equals(curTag, tagStack.peek())) {
                String realTag = curTag.substring(1);
                int indexStart = indexStack.peek();
                int indexEnd = iw.backWhileNot('<');
                iw.nextWhileNot('>');

                // no tag inside check
                iw.back();
                int indexLast = iw.backWhileNot('>');

                iw.next();
                iw.nextWhileNot('>');

                if (indexLast == indexStack.peek()) {
                    mapStack.pop();
                    XmlObject value = new XmlObjectBox(parsePrimitiveValue(iw.getData().substring(indexStart + 1, indexEnd)));


                    XmlSpecialList<XmlObject> lst =  mapStack.peek().getOrDefault(realTag, new XmlSpecialList<>());
                    lst.add(value);
                    mapStack.peek().put(realTag, lst);

                    //System.out.println("value : " + value);
                } else {
                    // need mapToAdd add
                    XmlSpecialMap<String, XmlSpecialList<XmlObject>> mapToAdd = mapStack.pop();

                    XmlSpecialList<XmlObject> lst =  mapStack.peek().getOrDefault(realTag, new XmlSpecialList<>());
                    lst.add(mapToAdd);
                    mapStack.peek().put(realTag, lst);
                }


                indexStack.pop();
                tagStack.pop();

                //System.out.println("Закрыл");
                //System.out.println(tagStack);

            } else {
                indexStack.add(iw.getIndex());
                tagStack.add("/" + curTag);
                mapStack.add(new XmlSpecialMap<>());

                //System.out.println("Новый");
                //System.out.println(tagStack);

            }
            //.out.println("_-".repeat(20));
        }
        return toReturn;
    }

    public Object getByKeyLine( String keyLine) {
        List<String> keyList = parseKeyLine(keyLine);
        Object obj = map;
        for (String key : keyList) {
            Map<String, List<Object>> localMap = (Map<String, List<Object>>) obj;
            String subKey = key.substring(0, key.indexOf(":"));
            int index = Integer.parseInt(key.substring(key.indexOf(":") + 1));

            obj = localMap.get(subKey).get(index);
        }
        return obj;
    }

    private static List<String> parseKeyLine(String keyLine) {
        List<String> keyList = new java.util.ArrayList<>(List.of(keyLine.split("=>")));
        for (int i = 0; i < keyList.size(); i++) {
            if (!keyList.get(i).contains(":")) keyList.set(i, keyList.get(i) + ":0");
        }
        return keyList;
    }
    private static boolean isDouble(String value){
        try{
            Double.parseDouble(value);
            return true;
        } catch (NumberFormatException e){
            return false;
        }
    }
    private static boolean isInteger(String value){
        try{
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e){
            return false;
        }
    }
    private static Object parsePrimitiveValue(String value) {
        String valuerTrim = value.trim();
        if ("true".equals(valuerTrim)) return true;
        if ("false".equals(valuerTrim)) return false;
        if (isDouble(valuerTrim)) return Double.parseDouble(valuerTrim);
        if (isInteger(valuerTrim)) return Integer.parseInt(valuerTrim);
        return value;
    }

    public Object getMap() {
        return map;
    }

    @Override
    public String toString() {
        return "XMLParser{" +
                "map=" + map +
                '}';
    }
}
