package wolox.training.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpMethod;
import wolox.training.models.Book;
import wolox.training.models.User;

public class RestUtils {
    public static String ObjectToStringJSON (Object object) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(object);
    }

    public static String createBookUrl(
            HttpMethod method,
            int port,
            Long id,
            String param
    ) {
        String url = "";
        if (method == HttpMethod.POST) {
            url = String.format("http://localhost:%s/api/books", port);
        }

        if (method == HttpMethod.GET && id != null) {
            url = String.format("http://localhost:%s/api/books/%d", port, id);
        }

        if (method == HttpMethod.GET && param != null) {
            url = String.format("http://localhost:%s/api/books/?%s", port, param);
        }

        if (method == HttpMethod.PUT || method == HttpMethod.DELETE) {
            url = String.format("http://localhost:%s/api/books/%d", port, id);
        }

        return url;
    }
}
