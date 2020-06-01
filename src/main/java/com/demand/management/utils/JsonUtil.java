package com.demand.management.utils;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.StringWriter;

public class JsonUtil {
    private static ObjectMapper mapper = new ObjectMapper();

    public static String BeanToJson(Object obj) {
        StringWriter sw = new StringWriter();
        JsonGenerator gen = null;
        try {
            gen = new JsonFactory().createGenerator(sw);
            mapper.writeValue(gen, obj);
            gen.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return sw.toString();
    }

    public static <T> T JsonToBean(String jsonStr, Class<T> objClass)
            throws JsonParseException, JsonMappingException, IOException {
        return mapper.readValue(jsonStr, objClass);
    }
}
