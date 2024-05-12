package com.junghun.common.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.*;

public class ConvertUtils {
    private ConvertUtils() {
    }

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private static final String DELIMITER = ",";
    private static final TypeReference<HashMap<String, String>> typeReference = new TypeReference<HashMap<String, String>>() {
    };

    public static String getStringByList(List<String> list) {
        if (list.isEmpty()) {
            return null;
        }
        return String.join(DELIMITER, list);
    }

    public static List<String> getListByString(String list) {
        if (list == null || list.isEmpty()) {
            return new ArrayList<>();
        }
        return Arrays.asList(list.split(DELIMITER));
    }

    public static String getStringByMap(Map<String, String> map) {
        try {
            return objectMapper.writeValueAsString(map);
        } catch (JsonProcessingException exception) {
            return null;
        }
    }

    public static Map<String, String> getMapByString(String map) {
        try {
            if (map == null || map.isEmpty()) {
                return new HashMap<>();
            }
            return objectMapper.readValue(map, typeReference);
        } catch (JsonProcessingException exception) {
            return new HashMap<>();
        }

    }
}