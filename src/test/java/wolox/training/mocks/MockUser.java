package wolox.training.mocks;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import wolox.training.models.Book;
import wolox.training.models.User;

public class MockUser {
    static public User createOne(String password) {
        User user = new User();
        List<Book> books = new ArrayList<>();
        LocalDate birthdate = LocalDate.of(1981, 8, 26);

        user.setUsername("pepe");
        user.setPassword(password);
        user.setName("Pepe");
        user.setBirthdate(birthdate);
        user.setBooks(books);

        return user;
    }
}
