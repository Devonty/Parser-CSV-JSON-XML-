package ru.vsu.sc.parser;

import ru.vsu.sc.parser.utils.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class JSONParser {

    public JsonObject parsedJSON;

    public void parseByData(String data) {
        parsedJSON = parseJSONbyData(data);
    }

    public void parseByFileName(String filename) {
        parsedJSON = parseJSON(filename);
    }

    public JsonObject byKey(String key) {
        return ((JsonSpecialMap<String, JsonObject>) parsedJSON).get(key);
    }

    public JsonObject byIndex(int i) {
        return ((JsonSpecialList<JsonObject>) parsedJSON).get(i);
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

    public static JsonObject parseJSON(String filePath) {
        String jsonData = readFile(filePath);
        return parseJSONbyData(jsonData);
    }

    private static JsonObject parseJSONbyData(String jsonData) {
        return parseValue(new IndexWrapper(jsonData));
    }

    private static JsonSpecialMap<String, JsonObject> parseMap(IndexWrapper iw) {
        JsonSpecialMap<String, JsonObject> jsonMap = new JsonSpecialMap<>();

        // value
        int indexSave;

        iw.next();
        iw.skipSpaces();
        while (iw.charNow() != '}') {
            // Key Search
            iw.nextWhileNot('"');
            String key = parseString(iw);
            // Value
            iw.nextWhileNot(':');
            iw.next();
            iw.skipSpaces();
            JsonObject value = parseValue(iw);
            // Adding
            jsonMap.put(key, value);
            iw.skipSpaces();
        }
        iw.next();
        return jsonMap;
    }

    private static JsonObject parseValue(IndexWrapper iw) {
        iw.skipSpaces();
        Character chr = iw.charNow();
        if (chr == '[') return parseList(iw);
        if (chr == '{') return parseMap(iw);
        return new JsonObjectBox(parsePrimitiveValue(iw));
    }

    private static JsonSpecialList<JsonObject> parseList(IndexWrapper iw) {
        JsonSpecialList<JsonObject> list = new JsonSpecialList<>();
        iw.next();
        iw.skipSpaces();
        while (iw.charNow() != ']') {
            list.add(parseValue(iw));
            if (iw.nextWhileNotIn(",]") == ']') break;
            iw.next();
            iw.skipSpaces();
        }
        iw.next();
        return list;
    }

    private static String parseString(IndexWrapper iw) {
        int indexStart = iw.getIndex();
        boolean beforeIsBackSlash = false;
        if (iw.charNow() != '"') throw new RuntimeException("Must starts with (\" )");
        iw.next();
        while (true) {
            if (iw.charNow() == '\\') {
                beforeIsBackSlash = !beforeIsBackSlash;
            } else if (iw.charNow() == '"' && !beforeIsBackSlash) {
                return iw.getData().substring(indexStart + 1, iw.getIndex());
            } else {
                beforeIsBackSlash = false;
            }
            iw.next();
        }
    }

    private static Object parsePrimitiveValue(IndexWrapper iw) {
        if (iw.charNow() == '"') {
            String value = parseString(iw);
            iw.next();
            iw.nextWhileNotIn(",]}");
            return value;
        }

        int indexSave = iw.getIndex();
        iw.next();
        iw.nextWhileNotIn(",]}");
        String value = iw.getData().substring(indexSave, iw.getIndex()).trim();

        if ("true".equals(value)) return true;
        if ("false".equals(value)) return false;
        if (value.contains(".")) return Double.parseDouble(value);
        else return Integer.parseInt(value);

    }
}