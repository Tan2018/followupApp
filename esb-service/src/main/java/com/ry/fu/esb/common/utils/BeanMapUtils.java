package com.ry.fu.esb.common.utils;

import com.google.common.collect.Maps;
import com.ry.fu.esb.common.constants.Constants;
import com.ry.fu.esb.common.response.BaseModel;
import com.ry.fu.esb.medicaljournal.model.PayCashReq;
import com.ry.fu.esb.medicaljournal.model.PayCashReqVo;
import com.ry.fu.esb.medicaljournal.service.impl.PayServiceImpl;
import com.ry.fu.esb.medicaljournal.util.SignatureUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cglib.beans.BeanMap;

import javax.persistence.Table;
import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

/**
 * @author Nick
 * @version V1.0.0
 * @Date 2018/3/21 9:02
 * @description description
 */
public class BeanMapUtils {

    private static Logger logger = LoggerFactory.getLogger(BeanMapUtils.class);

    // Map --> Bean 2: 利用 org.springframework.beans.BeanUtils 工具类实现 Map --> Bean
    public static <T> T transMap2Bean(Map<String, Object> map, T obj) {
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();

            for (PropertyDescriptor property : propertyDescriptors) {
                String key = property.getName();

                if (map.containsKey(key)) {
                    Object value = map.get(key);
                    // 得到property对应的setter方法
                    Method setter = property.getWriteMethod();
                    setter.invoke(obj, value);
                }

            }

        } catch (Exception e) {
            System.out.println("transMap2Bean Error " + e);
        }
        return obj;
    }

    /**
     * Bean 转为 Map
     * @param bean
     * @param <T>
     * @return
     */
    public static <T> Map<String, Object> beanToMap(T bean) {
        Map<String, Object> map = Maps.newHashMap();
        if (bean != null) {
            BeanMap beanMap = BeanMap.create(bean);
            for (Object key : beanMap.keySet()) {
                map.put(String.valueOf(key), beanMap.get(key));
            }
        }
        return map;
    }

    /**
     * 根据反射获取 BaseModel 上面注解了@Table的name值
     * @return
     */

    public static <E extends BaseModel> String getTableName(E e) {
        Table table = e.getClass().getAnnotation(Table.class);
        if(table != null) {
            return table.name().toString();
        }
        return null;
    }

    /**
     * 根据反射获取 BaseModel 上面注解了@Table的name值 SQUENCES.M_XXX_SEQ
     * @return
     */
    public static <E extends BaseModel> String getTableSeqKey(E e) {
        String tableName = getTableName(e);
        if(tableName == null) {
            return null;
        }
        return Constants.SQUENCES + tableName + "_SEQ";
    }

}
