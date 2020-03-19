package wolox.training.services.connectors.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Data;

@Data
public class OpenLibraryResponse {
    protected String title;
    protected String subtitle;
    protected List<Publisher> publishers;
    @JsonProperty("publish_date")
    protected String publishDate;
    @JsonProperty("number_of_pages")
    protected Integer pages;
    protected List<Author> authors;
    protected List<Subject> subjects;
    protected Cover cover;
}
