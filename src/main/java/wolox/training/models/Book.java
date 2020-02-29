package wolox.training.models;

import com.google.common.base.Preconditions;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

@Entity
@ApiModel(description = "Books on the library")
@Data
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Setter(AccessLevel.NONE)
    private long id;

    @Column()
    private String genre;

    @ApiModelProperty(notes = "The book's author")
    @Column(nullable = false)
    private String author;

    @Column(nullable = false)
    private String image;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String subtitle;

    @Column(nullable = false)
    private String publisher;

    @Column(nullable = false)
    private String year;

    @Column(nullable = false)
    private Integer pages;

    @Column(nullable = false)
    private String isbn;

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setAuthor(String author) {
        Preconditions.checkNotNull(author);
        this.author = author;
    }

    public void setImage(String image) {
        Preconditions.checkNotNull(image);
        this.image = image;
    }

    public void setTitle(String title) {
        Preconditions.checkNotNull(title);
        this.title = title;
    }

    public void setSubtitle(String subtitle) {
        Preconditions.checkNotNull(subtitle);
        this.subtitle = subtitle;
    }

    public void setPublisher(String publisher) {
        Preconditions.checkNotNull(publisher);
        this.publisher = publisher;
    }

    public void setYear(String year) {
        Preconditions.checkNotNull(year);
        this.year = year;
    }

    public void setPages(Integer pages) {
        Preconditions.checkNotNull(pages);
        this.pages = pages;
    }

    public void setIsbn(String isbn) {
        Preconditions.checkNotNull(isbn);
        this.isbn = isbn;
    }
}
