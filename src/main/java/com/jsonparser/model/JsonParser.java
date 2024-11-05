package com.jsonparser.model;

import org.json.JSONArray;
import org.json.JSONObject;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonParser {

    // Function to parse any valid JSON string into an Object, List, or Map
    public static Object parse(String json) {
        json = json.trim();
        if (json.startsWith("{")) {
            return parseJsonObject(new JSONObject(json));
        } else if (json.startsWith("[")) {
            return parseJsonArray(new JSONArray(json));
        } else {
            return parsePrimitive(json);
        }
    }

    private static Map<String, Object> parseJsonObject(JSONObject jsonObject) {
        Map<String, Object> map = new HashMap<>();
        for (String key : jsonObject.keySet()) {
            Object value = jsonObject.get(key);
            map.put(key, value instanceof JSONObject ? parseJsonObject((JSONObject) value) :
                       value instanceof JSONArray ? parseJsonArray((JSONArray) value) : parsePrimitive(value));
        }
        return map;
    }
    
    private static Object parsePrimitive(Object value) {
        if (value instanceof String) {
            String strValue = (String) value;
            if (strValue.equals("null")) return null;
            if (strValue.equals("true")) return true;
            if (strValue.equals("false")) return false;
            try {
                if (strValue.contains(".")) {
                    return new BigDecimal(strValue);
                } else {
                    return new BigInteger(strValue);
                }
            } catch (NumberFormatException e) {
                return strValue;
            }
        }
        return value;
    
    }

    private static List<Object> parseJsonArray(JSONArray jsonArray) {
        return jsonArray.toList();
    }
    
    private static void printResult(Object result) {
        if (result instanceof Map) {
            Map<?, ?> map = (Map<?, ?>) result;
            System.out.println("Parsed Object:");
            for (Map.Entry<?, ?> entry : map.entrySet()) {
                System.out.printf("%s: %s%n", entry.getKey().toString(), entry.getValue().toString());
            }
        } else if (result instanceof List) {
            List<?> list = (List<?>) result;
            System.out.println("Parsed Array:");
            for (Object item : list) {
                System.out.println(item.toString());
            }
        } else {
            System.out.println("Parsed Value: " + result.toString());
        }
    }

    public static void main(String[] args) {
        String json = "{\"name\": \"John\", \"age\": 30, \"height\": 5.9, \"isStudent\": false, \"courses\": [\"Math\", \"Science\"]}";
        Object result = parse(json);
        printResult(result);

        String json1 = "[\n    \"JSON Test Pattern pass1\",\n    {\"object with 1 member\":[\"array with 1 element\"]},\n    {},\n    [],\n    -42,\n    true,\n    false,\n    {\n        \"integer\": 1234567890,\n        \"real\": -9876.543210,\n        \"e\": 0.123456789e-12,\n        \"E\": 1.234567890E+34,\n        \"\":  23456789012E66,\n        \"zero\": 0,\n        \"one\": 1,\n        \"space\": \" \",\n        \"quote\": \"\\\"\",\n        \"controls\": \"\\b\\f\\n\\r\\t\",\n        \"slash\": \"/ & \\/\",\n        \"alpha\": \"abcdefghijklmnopqrstuvwyz\",\n        \"ALPHA\": \"ABCDEFGHIJKLMNOPQRSTUVWYZ\",\n        \"digit\": \"0123456789\",\n        \"0123456789\": \"digit\",\n        \"special\": \"`1~!@#$%^&*()_+-={':[,]}|;.</>?\",\n        \"hex\": \"\\u0123\\u4567\\u89AB\\uCDEF\\uabcd\\uef4A\",\n        \"true\": true,\n        \"false\": false,\n        \"null\": null,\n        \"array\":[  ],\n        \"object\":{  },\n        \"address\": \"50 St. James Street\",\n        \"url\": \"http://www.JSON.org/\",\n        \"comment\": \"// /* <!-- --\",\n        \"# -- --> */\": \" \",\n        \" s p a c e d \" :[1,2 , 3\n\n,\n\n4 , 5        ,          6           ,7        ],\"compact\":[1,2,3,4,5,6,7],\n        \"jsontext\": \"{\\\"object with 1 member\\\":[\\\"array with 1 element\\\"]}\",\n        \"quotes\": \"&#34; \\u0022 %22 0x22 034 &#x22;\",\n        \"\\/\\\\\\\"\\uCAFE\\uBABE\\uAB98\\uFCDE\\ubcda\\uef4A\\b\\f\\n\\r\\t`1~!',./<>?@#$%^&*()_+-=[]{}|;:\"\n: \"A key can be any string\"\n    },\n    0.5 ,98.6\n,\n99.44\n,\n\n1066,\n1e1,\n0.1e1,\n1e-1,\n1e00,2e+00,2e-00\n,\"rosebud\"]";
        System.out.println("\n\n");
        result = parse(json1);
        printResult(result);
    }
}
