package com.ry.fu.followup.base.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ry.fu.followup.base.jackson.JacksonBeanSerializerModifier;
import com.ry.fu.followup.base.jackson.SerializerFeature;
import com.ry.fu.followup.pwp.model.Account;

import java.util.ArrayList;
import java.util.List;

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
     * 把JavaBean转换为json字符串
     *
     * @param object
     * @return
     */
    public static String toJSonNullAsEmpty(Object object) {
        if (objectMapper == null) {
            objectMapper = new ObjectMapper();
        }

        objectMapper.setSerializerFactory(objectMapper.getSerializerFactory()
                .withSerializerModifier(
                        new JacksonBeanSerializerModifier(
                                SerializerFeature.WriteNullListAsEmpty,
                                SerializerFeature.WriteNullStringAsEmpty,
                                SerializerFeature.WriteNullNumberAsZero,
                                SerializerFeature.WriteNullBooleanAsFalse
                        )));

        try {
            return objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 把JavaBean转换为json字符串
     *
     * @param object
     * @return
     */
    public static String toJSon(Object object, SerializerFeature... features) {
        if (objectMapper == null) {
            objectMapper = new ObjectMapper();
        }

        objectMapper.setSerializerFactory(objectMapper.getSerializerFactory()
                .withSerializerModifier(
                        new JacksonBeanSerializerModifier(
                                SerializerFeature.WriteNullListAsEmpty,
                                SerializerFeature.WriteNullStringAsEmpty,
                                SerializerFeature.WriteNullNumberAsZero,
                                SerializerFeature.WriteNullBooleanAsFalse
                        )));

        try {
            return objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void main(String[] args) {
        List<Account> list = new ArrayList<Account>();
        Account account1 = new Account();
        account1.setAccountName("张三");
        account1.setAccountCode("zhangsan1");

        Account account2 = new Account();
        account2.setAccountName("张三");
        account2.setAccountCode("zhangsan2");

        list.add(account1);
        list.add(account2);
        String json = JsonUtils.toJSon(list);
        System.out.println(json);

        ArrayList<Account> list2 = (ArrayList<Account>) JsonUtils.readValue(json, List.class, Account.class);
        for (Account account : list2) {
            System.out.println(account.getAccountName() + "------------" + account.getAccountCode());
        }
    }

}
