package wolox.training.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import wolox.training.models.Book;

@Data
public class UserDTO {
    private Long id;
    private String username;
    private String password;
    private String name;
    private LocalDate birthdate;
    private List<Book> books = new ArrayList<>();
}
