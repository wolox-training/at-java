package wolox.training.services.connectors;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.Stream;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import wolox.training.exceptions.RequestException;
import wolox.training.services.connectors.dtos.OpenLibraryResponse;

@Service
public class OpenLibraryRequester {

    private RestTemplate restTemplate;

    public OpenLibraryRequester(RestTemplateBuilder restTemplateBuilder){
        this.restTemplate = restTemplateBuilder.build();
    }

    public Optional<OpenLibraryResponse> getBookByIsbn(String isbn) {
        String url = "https://openlibrary.org/api/books?bibkeys=ISBN:" + isbn + "&format=json&jscmd=data";

        ResponseEntity<Map<String, OpenLibraryResponse>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Map<String, OpenLibraryResponse>>() {}
        );

        Map<String, OpenLibraryResponse> body = response
                .getBody();

        if (body == null) {
           throw new RequestException();
        }

        Stream<Entry<String, OpenLibraryResponse>> responseStream = body
                .entrySet()
                .stream();

        return responseStream.map(Entry::getValue).findFirst();
    }

}
