package cb.fm.backtowork.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

@Component
public class JsonUtils {

    public static String toJsonString(Object myObj) {
        ObjectMapper om = new ObjectMapper();
        om.setSerializationInclusion(com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL);
        try {
            return om.writeValueAsString(myObj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return "";
    }

}
