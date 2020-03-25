package wolox.training.connectors;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import wolox.training.constants.Urls;
import wolox.training.models.Book;

public class BookConnector {
    private RestTemplate restTemplate;

    public BookConnector(RestTemplateBuilder restTemplateBuilder){
        this.restTemplate = restTemplateBuilder.build();
    }

    public Book getBook(Long id) {
        String url = String.format("%s/%d", Urls.BOOKS, id);

        ResponseEntity<Book> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                Book.class
        );

        return response.getBody();
    }
}
