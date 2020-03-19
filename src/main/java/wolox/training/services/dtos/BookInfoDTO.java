package wolox.training.services.dtos;

import com.google.common.base.Preconditions;
import java.util.List;
import lombok.Data;
import wolox.training.exceptions.ErrorConstats;

@Data
public class BookInfoDTO {
    protected Long id;
    protected String isbn;
    protected String title;
    protected String subtitle;
    protected List<String> publishers;
    protected String publishDate;
    protected Integer pages;
    protected List<String> authors;

    public void setIsbn(String isbn) {
        Preconditions.checkNotNull(isbn, ErrorConstats.FIELD_CANNOT_BE_EMPTY, "isbn");
        Preconditions.checkArgument(!isbn.equals(""), ErrorConstats.FIELD_CANNOT_BE_EMPTY, "isbn");
        this.isbn = isbn;
    }

    public void setTitle(String title) {
        Preconditions.checkNotNull(title, ErrorConstats.FIELD_CANNOT_BE_EMPTY, "title");
        Preconditions.checkArgument(!title.equals(""), ErrorConstats.FIELD_CANNOT_BE_EMPTY, "title");
        this.title = title;
    }

    public void setPublishers(List<String> publishers) {
        Preconditions.checkNotNull(publishers, ErrorConstats.FIELD_CANNOT_BE_EMPTY, "publishers");
        this.publishers = publishers;
    }

    public void setPublishDate(String publishDate) {
        Preconditions.checkNotNull(publishDate, ErrorConstats.FIELD_CANNOT_BE_EMPTY, "year");
        Preconditions.checkArgument(!publishDate.equals(""), ErrorConstats.FIELD_CANNOT_BE_EMPTY, "year");
        this.publishDate = publishDate;
    }

    public void setPages(Integer pages) {
        int pageAmount = 10;
        Preconditions.checkNotNull(pages, ErrorConstats.FIELD_CANNOT_BE_EMPTY, "pages");
        Preconditions.checkArgument(pages > pageAmount, ErrorConstats.MINIMUM_PAGE_AMOUNT_MESSAGE, pageAmount);
        this.pages = pages;
    }

    public void setAuthors(List<String> authors) {
        Preconditions.checkNotNull(authors, ErrorConstats.FIELD_CANNOT_BE_EMPTY, "author");
        this.authors = authors;
    }
}
