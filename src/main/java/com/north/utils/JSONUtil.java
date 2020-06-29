package com.north.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.StdDateFormat;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.shiro.session.mgt.SimpleSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

import java.util.List;

public class JSONUtil {

    private static Logger logger = LoggerFactory.getLogger(JSONUtil.class);

    private String flag = "1";

    public Boolean getA() {
        return flag.equalsIgnoreCase("1");
    }

    public Boolean[] getB() {
        return new Boolean[]{flag.equalsIgnoreCase("1")};
    }

    public static void main(String[] args) throws Exception {
        SimpleSession ss = new SimpleSession();
        ss.setHost("127.0.0.1");
        String s = JSONUtil.parseObjectToJSONString(ss);
//        System.out.println(s);
        s = "{\"@class\":\"org.apache.shiro.session.mgt.SimpleSession\",\"id\":\"e252e318-06e8-423c-b56c-25a502821f6e\",\"startTimestamp\":[\"java.util.Date\",\"2020-06-28T04:26:41.160+0000\"],\"stopTimestamp\":null,\"lastAccessTime\":[\"java.util.Date\",\"2020-06-28T04:26:41.160+0000\"],\"timeout\":1800000,\"expired\":false,\"host\":\"127.0.0.1\",\"attributes\":{\"@class\":\"java.util.HashMap\"},\"valid\":true,\"attributeKeys\":[\"java.util.HashMap$KeySet\",[]],\"attributesLazy\":{\"@class\":\"java.util.HashMap\"},\"alteredFieldsBitMask\":91,\"stopped\":false,\"timedOut\":false}";

//        ObjectMapper mapper = new ObjectMapper();
////        mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
//        mapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance ,
//                ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);
//        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
//        mapper.setDefaultPropertyInclusion(JsonInclude.Include.NON_DEFAULT);
//        mapper.setDateFormat(new StdDateFormat());
//        JavaTimeModule javaTimeModule = new JavaTimeModule();
//        mapper.registerModule(javaTimeModule);

        Jackson2JsonRedisSerializer serializer = new Jackson2JsonRedisSerializer(SimpleSession.class);
        SimpleSession simpleSession =  (SimpleSession)serializer.deserialize(s.getBytes());
//        SimpleSession simpleSession =  mapper.readValue(s,SimpleSession.class);

        System.out.println(simpleSession.toString());

//        JSONUtil ss = new JSONUtil();
//        String s = JSONUtil.parseObjectToJSONString(ss);
//        System.out.println(s);
//        JSONUtil simpleSession = JSONUtil.parseJSONToObject(s,JSONUtil.class);
//        System.out.println(simpleSession.getA());
//        System.out.println(simpleSession.getB());
    }

    public static<T> T  parseJSONToObject(String jsonString,Class<T> clazz){
        T deserializedPerson = null;
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
            mapper.setDefaultPropertyInclusion(JsonInclude.Include.NON_DEFAULT);
            mapper.setDateFormat(new StdDateFormat());
            JavaTimeModule javaTimeModule = new JavaTimeModule();
            mapper.registerModule(javaTimeModule);
            deserializedPerson = mapper.readValue(jsonString, clazz);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException("json format " +clazz.toString()+ " exception");
        }
        return deserializedPerson;
    }

    public static<T> List<T> parseJSONArrayToList(String jsonString, Class<T> clazz){
        List<T> deserializedPerson = null;
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
            mapper.setDefaultPropertyInclusion(JsonInclude.Include.NON_DEFAULT);
            mapper.setDateFormat(new StdDateFormat());
            JavaType javaType = mapper.getTypeFactory().constructParametricType(List    .class, clazz);
            JavaTimeModule javaTimeModule = new JavaTimeModule();
            mapper.registerModule(javaTimeModule);
            deserializedPerson = mapper.readValue(jsonString, javaType);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException("json format " +clazz.toString()+ " exception");
        }
        return deserializedPerson;
    }

    public static<T> String  parseObjectToJSONString(T object){
        String jsonString = null;
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS,false);
            mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS,false);
            mapper.setDateFormat(new StdDateFormat());
            JavaTimeModule javaTimeModule = new JavaTimeModule();
            mapper.registerModule(javaTimeModule);
            jsonString = mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            logger.error(object.getClass().toString()+"format json exception",e);
            throw new RuntimeException(object.getClass().toString()+"format json exception");
        }
        return jsonString;
    }



}
