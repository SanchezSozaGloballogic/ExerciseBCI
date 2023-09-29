package cl.rs.bci.exercise.BCIExcercise.util;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.InputStream;

public class TestUtil {

    public static <T> T jsonSourceToObject(String datasources, Class<T> t){
        try{
            InputStream jsonStream = TestUtil.class.getClassLoader().getResourceAsStream(datasources);
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            objectMapper.configure(DeserializationFeature.FAIL_ON_MISSING_CREATOR_PROPERTIES, false);
            return objectMapper.readValue(jsonStream, t);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return null;
    }
}
