package com.utils;

import java.util.regex.Pattern;

public class Patterns {

    public static Boolean containsNumber(String stringToCheck) {
        Pattern pattern = Pattern.compile("[0-9]");
        Boolean found = pattern.matcher(stringToCheck).find();
        return found;
    }

}
