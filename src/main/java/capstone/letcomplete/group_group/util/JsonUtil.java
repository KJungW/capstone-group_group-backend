package capstone.letcomplete.group_group.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class JsonUtil {
    private final ObjectMapper objectMapper = new ObjectMapper();

    public String convertObjectToJson(Object obj) throws JsonProcessingException {
        return objectMapper.writeValueAsString(obj);
    }

    public  <T> T convertJsonToObject(String json,  Class<T> clazz) throws JsonProcessingException {
        return objectMapper.readValue(json, clazz);
    }

    public <T> List<T> convertJsonToList(String json, Class<T> clazz) throws JsonProcessingException {
        return objectMapper.readValue(json, new TypeReference<List<T>>() {});
    }
}
