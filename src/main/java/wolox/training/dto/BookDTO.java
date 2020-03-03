package wolox.training.dto;

import lombok.Data;

@Data
public class BookDTO {
    protected Long id;
    protected String genre;
    protected String author;
    protected String image;
    protected String title;
    protected String subtitle;
    protected String publisher;
    protected String year;
    protected Integer pages;
    protected String isbn;
}
