package wolox.training.models;

import com.google.common.base.Preconditions;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.annotation.Nonnull;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import wolox.training.exceptions.ErrorConstats;

@Entity
@ApiModel(description = "Books on the library")
@Data
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Setter(AccessLevel.NONE)
    protected Long id;

    @Column()
    protected String genre;

    @ApiModelProperty(notes = "The book's author")
    @Column(nullable = false)
    protected String author;

    @Column(nullable = false)
    protected String image;

    @Column(nullable = false)
    protected String title;

    @Column(nullable = false)
    protected String subtitle;

    @Column(nullable = false)
    protected String publisher;

    @Column(nullable = false)
    protected String year;

    @Column(nullable = false)
    protected Integer pages;

    @Column(nullable = false)
    protected String isbn;

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setAuthor(String author) {
        Preconditions.checkNotNull(author, ErrorConstats.FIELD_CANNOT_BE_EMPTY, "author");
        Preconditions.checkArgument(!author.equals(""), ErrorConstats.FIELD_CANNOT_BE_EMPTY, "author");
        this.author = author;
    }

    public void setImage(String image) {
        Preconditions.checkNotNull(image, ErrorConstats.FIELD_CANNOT_BE_EMPTY, "image");
        Preconditions.checkArgument(!image.equals(""), ErrorConstats.FIELD_CANNOT_BE_EMPTY, "image");
        this.image = image;
    }

    public void setTitle(String title) {
        Preconditions.checkNotNull(title, ErrorConstats.FIELD_CANNOT_BE_EMPTY, "title");
        Preconditions.checkArgument(!title.equals(""), ErrorConstats.FIELD_CANNOT_BE_EMPTY, "title");
        this.title = title;
    }

    public void setSubtitle(String subtitle) {
        Preconditions.checkNotNull(subtitle, ErrorConstats.FIELD_CANNOT_BE_EMPTY, "subtitle");
        this.subtitle = subtitle;
    }

    public void setPublisher(String publisher) {
        Preconditions.checkNotNull(publisher, ErrorConstats.FIELD_CANNOT_BE_EMPTY, "publisher");
        Preconditions.checkArgument(!publisher.equals(""), ErrorConstats.FIELD_CANNOT_BE_EMPTY, "publisher");
        this.publisher = publisher;
    }

    public void setYear(String year) {
        Preconditions.checkNotNull(year, ErrorConstats.FIELD_CANNOT_BE_EMPTY, "year");
        Preconditions.checkArgument(!year.equals(""), ErrorConstats.FIELD_CANNOT_BE_EMPTY, "year");
        this.year = year;
    }

    public void setPages(Integer pages) {
        int pageAmount = 10;
        Preconditions.checkNotNull(pages, ErrorConstats.FIELD_CANNOT_BE_EMPTY, "pages");
        Preconditions.checkArgument(pages > pageAmount, ErrorConstats.MINIMUM_PAGE_AMOUNT_MESSAGE, pageAmount);
        this.pages = pages;
    }

    public void setIsbn(String isbn) {
        Preconditions.checkNotNull(isbn, ErrorConstats.FIELD_CANNOT_BE_EMPTY, "isbn");
        Preconditions.checkArgument(!isbn.equals(""), ErrorConstats.FIELD_CANNOT_BE_EMPTY, "isbn");
        this.isbn = isbn;
    }
}
