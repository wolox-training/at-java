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
import wolox.training.exceptions.ErrorConstats;
import wolox.training.security.PasswordManager;
import wolox.training.utils.ValidateString;

@Entity
@Table(name="users")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Setter(AccessLevel.NONE)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column
    private String password;

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
        this.username = ValidateString.exists(username, "username");
    }

    public void setPassword(String password) {
        ValidateString.exists(password, "password");
        this.password = PasswordManager.encode(password);
    }

    public void setName(String name) {
        this.name = ValidateString.exists(name, "name");
    }

    public void setBirthdate(LocalDate birthdate) {
        Preconditions.checkArgument(birthdate != null, ErrorConstats.FIELD_CANNOT_BE_EMPTY, "birthdate");
        Preconditions.checkArgument(birthdate.isBefore(LocalDate.now()), ErrorConstats.INVALID_DATE, "birthdate");
        this.birthdate = birthdate;
    }

    public void setBooks(List<Book> books) {
        Preconditions.checkArgument(books != null, ErrorConstats.FIELD_CANNOT_BE_EMPTY, "books");
        this.books = books;
    }

    public void addBook(Book book) {
        Preconditions.checkArgument(book != null, ErrorConstats.FIELD_CANNOT_BE_EMPTY, "book");
        if(this.books.contains(book)) throw new BookAlreadyOwnedException(book.getTitle());
        this.books.add(book);
    }

    public void removeBook(Book book) {
        Preconditions.checkArgument(book != null, ErrorConstats.FIELD_CANNOT_BE_EMPTY, "book");
        this.books.remove(book);
    }

}
