package ru.vsu.sc.parser;


import ru.vsu.sc.parser.utils.XmlObject;
import ru.vsu.sc.parser.utils.XmlSpecialList;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class Main {

    public static void testJSON1() {
        JSONParser parser = new JSONParser();
        parser.parseByFileName("src/ru/vsu/sc/parser/File/data1.json") ;
        myPrint(parser.parsedJSON);
    }

    public static void testJSON2() {
        JSONParser parser = new JSONParser();
        parser.parseByFileName("src/ru/vsu/sc/parser/File/data2.json");

        myPrint(parser.parsedJSON);

        Object result;

        System.out.println("1-".repeat(20));
        result = parser.byKey("catalogs");
        myPrint(result);

        System.out.println("2-".repeat(20));
        result = parser.byKey("catalogs").byIndex(0);
        myPrint(result);

        System.out.println("3-".repeat(20));
        result = parser.byKey("catalogs").byIndex(0).byKey("conditions");
        myPrint(result);

        System.out.println("4-".repeat(20));
        result = parser.byKey("catalogs").byIndex(0).byKey("conditions").open();
        myPrint(result);

    }

    public static void testCSV1() {
        String path = "src/ru/vsu/sc/parser/File/data.csv";
        String delimiter = ";";
        CSVParser parser = new CSVParser(path, delimiter);
        parser.parseCSV();
    }

    public static void testXML1Old() {
        XMLParser xmlParser = new XMLParser("src/ru/vsu/sc/parser/File/data2.xml");
        myPrint(xmlParser.getMap(), 0);

        String keyLine = "PurchaseOrder=>Address:0=>Street";
        System.out.println('"' + keyLine + '"' + " : " +  '"' +xmlParser.getByKeyLine(keyLine) + '"');

        keyLine = "PurchaseOrder=>Address:1=>Street";
        System.out.println('"' + keyLine + '"' + " : " +  '"' +xmlParser.getByKeyLine(keyLine) + '"');

        keyLine = "PurchaseOrder=>DeliveryNotes";
        System.out.println('"' + keyLine + '"' + " : " +  '"' +xmlParser.getByKeyLine(keyLine) + '"');

        keyLine = "PurchaseOrder=>Items=>Item:1=>ProductName:0";
        System.out.println('"' + keyLine + '"' + " : " +  '"' +xmlParser.getByKeyLine(keyLine) + '"');
    }

    public static void testXML1() {
        XMLParser xmlParser = new XMLParser("src/ru/vsu/sc/parser/File/data2.xml");
        myPrint(xmlParser.getMap(), 0);

        Object result;

        System.out.println("1-".repeat(20));
        result = xmlParser.byKey("PurchaseOrder");
        myPrint(result);

        System.out.println("2-".repeat(20));
        result = xmlParser.byKey("PurchaseOrder").byKey("Address");
        myPrint(result);

        System.out.println("3-".repeat(20));
        result = xmlParser.byKey("PurchaseOrder").byKey("Address").byIndex(0);
        myPrint(result);

        System.out.println("4-".repeat(20));
        result = xmlParser.byKey("PurchaseOrder").byKey("Address").byIndex(0).byKey("Zip");
        myPrint(result);

        System.out.println("5-".repeat(20));
        result = xmlParser.byKey("PurchaseOrder").byKey("Address").byIndex(0).byKey("Zip").open();
        myPrint(result);


    }

    public static void main(String[] args) {
        testXML1();
        //testCSV1();
        //testJSON1();
        //testJSON2();

    }

    public static void myPrint(Object o) {
        myPrint(o, 0);
    }
    public static void myPrint(Object o, int n) {
        if (o instanceof Map<?, ?>) {
            Map<String, Object> map = (Map<String, Object>) o;
            System.out.println("\t".repeat(n) + "{");
            for (String key : map.keySet()) {
                System.out.println("\t".repeat(n) + "\"" + key + "\" : ");
                myPrint(map.get(key), n + 1);
            }
            System.out.println("\t".repeat(n) + "}");
        } else if (o instanceof List<?>) {
            System.out.println("\t".repeat(n) + "[");
            for (Object obj : (List<?>) o) {
                myPrint(obj, n + 1);
            }
            System.out.println("\t".repeat(n) + "]");

        } else if (o instanceof String) {
            System.out.println("\t".repeat(n) + "\"" + o + "\",");
        } else System.out.println("\t".repeat(n) + o + ",");
    }
}
