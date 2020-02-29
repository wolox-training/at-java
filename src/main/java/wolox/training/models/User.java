package wolox.training.models;

import com.google.common.base.Preconditions;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
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

@Entity
@Table(name="users")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Setter(AccessLevel.NONE)
    private long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private LocalDate birthdate;

    @OneToMany
    @Column(nullable = false)
    private List<Book> books = new ArrayList<>();

    public long getId() {
        return id;
    }

    public void setUsername(String username) {
        Preconditions.checkNotNull(username);
        this.username = username;
    }

    public void setName(String name) {
        Preconditions.checkNotNull(name);
        this.name = name;
    }

    public void setBirthdate(LocalDate birthdate) {
        Preconditions.checkNotNull(birthdate);
        this.birthdate = birthdate;
    }

    public void setBooks(List<Book> books) {
        Preconditions.checkNotNull(books);
        this.books = books;
    }

    public void addBook(Book book) {
        Preconditions.checkNotNull(book);
        if(this.books.contains(book)) throw new BookAlreadyOwnedException(book.getTitle());
        this.books.add(book);
    }

    public void removeBook(Book book) {
        Preconditions.checkNotNull(book);
        this.books.remove(book);
    }

}
