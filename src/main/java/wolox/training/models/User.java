package wolox.training.models;

import com.google.common.base.Preconditions;
import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;
import java.time.chrono.Chronology;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import wolox.training.exceptions.BookAlreadyOwnedException;
import wolox.training.exceptions.ErrorConstats;

@Entity
@Table(name="users")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Setter(AccessLevel.NONE)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private LocalDate birthdate;

    @OneToMany
    @Column(nullable = false)
    private List<Book> books = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setUsername(String username) {
        Preconditions.checkNotNull(username, ErrorConstats.FIELD_CANNOT_BE_EMPTY, "username");
        Preconditions.checkArgument(!username.equals(""), ErrorConstats.FIELD_CANNOT_BE_EMPTY, "username");
        this.username = username;
    }

    public void setName(String name) {
        Preconditions.checkNotNull(name, ErrorConstats.FIELD_CANNOT_BE_EMPTY, "name");
        Preconditions.checkArgument(!name.equals(""), ErrorConstats.FIELD_CANNOT_BE_EMPTY, "name");
        this.name = name;
    }

    public void setBirthdate(LocalDate birthdate) {
        Preconditions.checkNotNull(birthdate, ErrorConstats.FIELD_CANNOT_BE_EMPTY, "birthdate");
        Preconditions.checkArgument(birthdate.isBefore(LocalDate.now()), ErrorConstats.INVALID_DATE, "birthdate");
        this.birthdate = birthdate;
    }

    public void setBooks(List<Book> books) {
        Preconditions.checkNotNull(books, ErrorConstats.FIELD_CANNOT_BE_EMPTY, "books");
        this.books = books;
    }

    public void addBook(Book book) {
        Preconditions.checkNotNull(book, ErrorConstats.FIELD_CANNOT_BE_EMPTY, "book");
        if(this.books.contains(book)) throw new BookAlreadyOwnedException(book.getTitle());
        this.books.add(book);
    }

    public void removeBook(Book book) {
        Preconditions.checkNotNull(username, ErrorConstats.FIELD_CANNOT_BE_EMPTY, username);
        this.books.remove(book);
    }

}
