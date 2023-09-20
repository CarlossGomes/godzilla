package com.application.godzilla.util;

public class Util {

    public static String removeCaracteresEspeciais(String doc) {
        if (doc.contains(".")) {
            doc = doc.replace(".", "");
        }
        if (doc.contains("-")) {
            doc = doc.replace("-", "");
        }
        if (doc.contains("/")) {
            doc = doc.replace("/", "");
        }
        if (doc.contains("(")) {
            doc = doc.replace("(", "");
        }
        if (doc.contains(")")) {
            doc = doc.replace(")", "");
        }
        return doc;
    }
}
