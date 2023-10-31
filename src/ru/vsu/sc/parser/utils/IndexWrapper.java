package ru.vsu.sc.parser.utils;

import java.util.Objects;

public class IndexWrapper {
    private int index = 0;
    private final String data;

    public IndexWrapper(String data) {
        this.data = data.trim();
    }

    public Character charNow() {
        return data.charAt(index);
    }

    public void next() {
        index++;
    }

    public void back() {
        index--;
    }

    public void skipSpaces() {
        while (Character.isWhitespace(data.charAt(index))) next();
    }

    public int nextWhileNot(Character chr) {
            /*
            stays if now on chr.
             */
        while (!Objects.equals(charNow(), chr)) next();
        return index;
    }

    public int backWhileNot(Character chr) {
            /*
            stays if now on chr.
             */
        while (!Objects.equals(charNow(), chr)) back();
        return index;
    }

    public Character nextWhileNotIn(String str) {
        while (!str.contains(String.valueOf(charNow()))) next();
        return charNow();
    }

    public int getIndex() {
        return index;
    }

    public String getData() {
        return data;
    }
}