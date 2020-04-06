package wolox.training.connectors;

import java.util.Base64;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import wolox.training.dto.BookDTO;
import wolox.training.services.dtos.BookInfoDTO;
import wolox.training.utils.RestUtils;

@Service
public class BookConnector {
    private RestTemplate restTemplate;

    public BookConnector(RestTemplateBuilder restTemplateBuilder){
        this.restTemplate = restTemplateBuilder.build();
    }

    private HttpHeaders createHeaders(String username, String password) {
        String authStr = String.format("%s:%s", username, password);
        String base64Creds = Base64.getEncoder().encodeToString(authStr.getBytes());

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + base64Creds);

        return headers;
    }

    public BookInfoDTO openLibraryReq(
            int port,
            String username,
            String password,
            String param
    ) {
        HttpMethod method = HttpMethod.GET;
        String url = RestUtils.createBookUrl(method, port, null, param);

        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

        HttpEntity<BookDTO> request = new HttpEntity<>(createHeaders(username, password));

        ResponseEntity<BookInfoDTO> response = restTemplate.exchange(
                url,
                method,
                request,
                BookInfoDTO.class
        );

        return response.getBody();
    }

    public BookDTO request(
            Long id,
            HttpMethod method,
            int port,
            String username,
            String password,
            BookDTO data,
            String param
    ) {
        String url = RestUtils.createBookUrl(method, port, id, param);

        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

        HttpEntity<BookDTO> request = new HttpEntity<>(data, createHeaders(username, password));

        ResponseEntity<BookDTO> response = restTemplate.exchange(
                url,
                method,
                request,
                BookDTO.class
        );

        return response.getBody();
    }

    public void deleteReq(
            Long id,
            int port,
            String username,
            String password
    ) {
        HttpMethod method = HttpMethod.DELETE;
        String url = RestUtils.createBookUrl(method, port, id, null);

        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

        HttpEntity<BookDTO> request = new HttpEntity<>(createHeaders(username, password));

        restTemplate.exchange(
                url,
                method,
                request,
                Void.class
        );
    }
}
