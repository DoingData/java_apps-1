package ca.jrvs.apps.twitter.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.IOException;

public class JsonUtil {

    /**
     * Parse JSON string to an object
     *
     * @param json  JSON string
     * @param clazz object class
     * @param <T>   Type
     * @return Object
     * @throws IOException
     */
    public static <T> T toObjectFromJson(String json, Class clazz) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return (T) mapper.readValue(json, clazz);
    }

    public static String toPrettyJson(Object object) throws JsonProcessingException {
        return toJson(object, true, false);
    }

    /**
     * Convert a java object to JSON string
     *
     * @param object            input object
     * @param prettyJson        if prettyJson is enabled it will write standard indentation
     * @param includeNullValues decides whether null values are written as an empty string or throw exception
     * @return JSON String
     * @throws JsonProcessingException
     */
    public static String toJson(Object object, boolean prettyJson, boolean includeNullValues) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        if (prettyJson) {
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
        }
        if (includeNullValues) {
            mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        }
        return mapper.writeValueAsString(object);
    }
}
