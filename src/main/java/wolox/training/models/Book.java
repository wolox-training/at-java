package wolox.training.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
public class Book {
    @Id
    @Getter
    @Setter
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Getter
    @Setter
    @Column()
    private String genre;

    @Getter
    @Setter
    @Column(nullable = false)
    private String author;

    @Getter
    @Setter
    @Column(nullable = false)
    private String image;

    @Getter
    @Setter
    @Column(nullable = false)
    private String title;

    @Getter
    @Setter
    @Column(nullable = false)
    private String subtitle;

    @Getter
    @Setter
    @Column(nullable = false)
    private String publisher;

    @Getter
    @Setter
    @Column(nullable = false)
    private String year;

    @Getter
    @Setter
    @Column(nullable = false)
    private int pages;

    @Getter
    @Setter
    @Column(nullable = false)
    private String isbn;
}
