package com.junghun.common.util;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ConvertUtils {
    private ConvertUtils() {}

    private static final String DELIMITER = ",";

    public static List<String> getListByString(String list) {
        if (list == null || list.isEmpty()) {
            return new ArrayList<>();
        }
        return Arrays.asList(list.split(DELIMITER));
    }

    public static String getStringByList(List<String> list) {
        if (list.isEmpty()) {
            return null;
        }
        return String.join(DELIMITER, list);
    }
}