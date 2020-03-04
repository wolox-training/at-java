package wolox.training.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import wolox.training.models.Book;
import wolox.training.models.User;

public class RestUtils {
    public static String ObjectToStringJSON (Object object) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(object);
    }
}
