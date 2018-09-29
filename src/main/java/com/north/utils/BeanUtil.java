package com.north.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;

public class BeanUtil {

    private static Logger logger = LoggerFactory.getLogger(BeanUtil.class);
    
    public static <T> T Map2Bean(Map<String, Object> map, Class<T> clazz) {
        Field[] fields = clazz.getDeclaredFields();
        T object = null;
        try {
            object = clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            logger.error("对象实例化失败：", e);
            throw new RuntimeException("对象实例化失败,检查对象是否有无参构造方法");
        }
        for (Entry<String, Object> entry : map.entrySet()) {
            String field = CamelToUnderlineUtil.underlineToCamel(entry.getKey());
            Class fieldType = getJavaType(fields, field);
            Object value = getValue(entry.getValue(), fieldType);
            String fieldName = toUpperCaseFirstOne(field);
            if (StringUtils.isEmpty(fieldName)) {
                continue;
            }
            String methodName = "set" + fieldName;
            try {
                Method method = clazz.getDeclaredMethod(methodName,fieldType);
                method.invoke(object, value);
            } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
                    | InvocationTargetException e) {
                logger.error("没有 该方法--------" + methodName, e);
            }

        }
        return object;
    }

    private static Object getValue(Object value , Class clazz) {
        if(Integer.class.equals(clazz)||int.class.equals(clazz)) {
            return Integer.valueOf(value.toString());
        } else if(Long.class.equals(clazz)||long.class.equals(clazz)) {
            return Long.valueOf(value.toString());
        } else if(Date.class.equals(clazz)) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                return sdf.parse(value.toString());
            } catch (ParseException e) {
                logger.error("Exception ", e);
                throw new RuntimeException("日期格式失败");
            }
        } else {
            return value;
        }
        
    }
    
    private static Class getJavaType(Field[] fields,String target) {
     
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            if(field.getName().toLowerCase().equals(target.toLowerCase())) {
                return field.getType();
            }
        }
        return null;
        
    }
    
    private static String toUpperCaseFirstOne(Object obj) {
        if (!(obj instanceof String)) {
            return "";
        }
        String s = obj.toString();
        if (Character.isUpperCase(s.charAt(0)))
            return s;
        else
            return (new StringBuilder()).append(Character.toUpperCase(s.charAt(0))).append(s.substring(1)).toString();
    }

}
