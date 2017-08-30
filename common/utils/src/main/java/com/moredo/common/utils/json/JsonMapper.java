package com.moredo.common.utils.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;

/**
 * Created by loop on 13-12-30.
 */
public class JsonMapper {

    /**
     * 将字符串转成实体类，允许斜杠等字符串
     */
    public static <T> T jsonToEntity(String json, Class<T> clazz) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        // 允许反斜杆等字符
        mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS,true);
        return mapper.readValue(json, clazz);
    }

    /**
     * 将字符串转成JsonNode，允许斜杠等字符串
     */
    public static JsonNode jsonToJsonNode(String json) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        // 允许反斜杆等字符

        mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS,true);
        //允许单引号
        mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES,true);
        return mapper.readValue(json, JsonNode.class);
    }

    public static  String objectToJson(Object object){
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerSubtypes(object.getClass());
        String reqJson = null;
        try {
            reqJson = mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return reqJson;
    }


}
