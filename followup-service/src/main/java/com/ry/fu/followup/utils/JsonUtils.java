package com.ry.fu.followup.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author Nick
 * @version V1.0.0
 * @Date 2017/12/5 10:02
 * @description 使用Jackson工具类
 */
public class JsonUtils {

    public static ObjectMapper objectMapper;

    /**
     * 使用泛型方法，把json字符串转换为相应的JavaBean对象。
     * (1)转换为普通JavaBean：readValue(json,Student.class)
     * (2)转换为List,如List<Student>,将第二个参数传递为Student
     * [].class.然后使用Arrays.asList();方法把得到的数组转换为特定类型的List
     *
     * @param jsonStr
     * @param valueType
     * @return
     */
    public static <T> T readValue(String jsonStr, Class<T> valueType) {
        if (objectMapper == null) {
            objectMapper = new ObjectMapper();
        }

        try {
            return objectMapper.readValue(jsonStr, valueType);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     *
     * @param jsonStr
     * @param valueTypeRef
     * @return
     */
    public static <T> T readValue(String jsonStr, TypeReference<T> valueTypeRef){
        if (objectMapper == null) {
            objectMapper = new ObjectMapper();
        }

        try {
            return objectMapper.readValue(jsonStr, valueTypeRef);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static <T> T readValue(String jsonStr, JavaType javaType) {
        if (objectMapper == null) {
            objectMapper = new ObjectMapper();
        }

        try {
            return objectMapper.readValue(jsonStr, javaType);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * json数组转List
     * @param jsonStr
     * @param collectionClass 集合的类型，比如List
     * @param elementClasses 对象的类型
     * @param <T>
     * @return
     */
    public static <T> T readValue(String jsonStr, Class<?> collectionClass, Class<?> elementClasses) {
        JavaType javaType = objectMapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);
        if (objectMapper == null) {
            objectMapper = new ObjectMapper();
        }

        try {
            return objectMapper.readValue(jsonStr, javaType);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private static JavaType getCollectionType(Class<?> collectionClass, Class<?>... elementClasses) {
        return objectMapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);
    }

    /**
     * 把JavaBean转换为json字符串
     *
     * @param object
     * @return
     */
    public static String toJSon(Object object) {
        if (objectMapper == null) {
            objectMapper = new ObjectMapper();
        }

        try {
            return objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    /**
     * 将list转为json
     * @param lists
     * @param sb
     * @return
     */
    public static StringBuilder listToJson(List<?> lists, StringBuilder sb) {
        if (sb == null) {
            sb = new StringBuilder();
        }
        sb.append("[");
        for (int i = 0; i < lists.size(); i++) {
            Object o = lists.get(i);
            if (o instanceof Map<?, ?>) {
                Map<?, ?> map = (Map<?, ?>) o;
                mapToJson(map, sb);
            } else if (o instanceof List<?>) {
                List<?> l = (List<?>) o;
                listToJson(l, sb);
            } else {
                sb.append("\"" + o + "\"");
            }
            if (i != lists.size() - 1) {
                sb.append(",");
            }
        }
        sb.append("]");
        return sb;
    }

    /**
     * 将map转为json
     * @param map
     * @param sb
     * @return
     */
    public static StringBuilder mapToJson(Map<?, ?> map, StringBuilder sb) {
        if(sb == null) {
            sb = new StringBuilder();
        }
        sb.append("{");
        Iterator<?> iter = map.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry<?, ?> entry = (Map.Entry<?, ?>) iter.next();
            String key = entry.getKey() != null ? entry.getKey().toString() : "";
            sb.append("\"" + stringToJson(key) + "\":");
            Object o = entry.getValue();
            if (o instanceof List<?>) {
                List<?> l = (List<?>) o;
                listToJson(l, sb);
            } else if (o instanceof Map<?, ?>) {
                Map<?, ?> m = (Map<?, ?>) o;
                mapToJson(m, sb);
            } else {
                String val = entry.getValue() != null ? entry.getValue().toString() : "";
                sb.append("\"" + stringToJson(val) + "\"");
            }
            if (iter.hasNext()){
                sb.append(",");
            }
        }
        sb.append("}");
        return sb;
    }

    /**
     * 将字符串转为json数据
     * @param str 数据字符串
     * @return json字符串
     */
    private static String stringToJson(String str) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            switch (c) {
                case '\"':
                    sb.append("\\\"");
                    break;
                case '\\':
                    sb.append("\\\\");
                    break;
                case '/':
                    sb.append("\\/");
                    break;
                case '\b':
                    sb.append("\\b");
                    break;
                case '\f':
                    sb.append("\\f");
                    break;
                case '\n':
                    sb.append("\\n");
                    break;
                case '\r':
                    sb.append("\\r");
                    break;
                case '\t':
                    sb.append("\\t");
                    break;
                default:
                    sb.append(c);
            }
        }
        return sb.toString();
    }

}
