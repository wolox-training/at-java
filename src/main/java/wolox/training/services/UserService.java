package wolox.training.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wolox.training.dto.UserDTO;
import wolox.training.dtoConverters.UserDtoConverter;
import wolox.training.exceptions.BookNotFoundException;
import wolox.training.exceptions.UserNotFoundException;
import wolox.training.models.Book;
import wolox.training.models.User;
import wolox.training.repositories.BookRepository;
import wolox.training.repositories.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;

    public UserDTO findByUsername(String username) {

        User user = userRepository
                .findByUsername(username)
                .orElseThrow(UserNotFoundException::new);

        return UserDtoConverter.convertUserToDTO(user);
    }

    public UserDTO findById(Long id) {
        User user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        return UserDtoConverter.convertUserToDTO(user);
    }

    public boolean userExistsById(Long id) {
        return userRepository.existsById(id);
    }

    public UserDTO createUser(UserDTO userDTO) {
        User user = UserDtoConverter.convertDtoToUser(userDTO);
        userRepository.save(user);
        return UserDtoConverter.convertUserToDTO(user);
    }

    public UserDTO updateUser(UserDTO userDTO) {
        User existingUser = userRepository.findById(userDTO.getId())
                .orElseThrow(UserNotFoundException::new);

        User user = UserDtoConverter.convertDtoToExistingUser(userDTO, existingUser);
        userRepository.save(user);

        return UserDtoConverter.convertUserToDTO(user);
    }

    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException();
        }

        userRepository.deleteById(id);
    }

    public UserDTO addBookToUser(Long userId, Long bookId) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        Book book = bookRepository.findById(bookId).orElseThrow(BookNotFoundException::new);
        user.addBook(book);
        userRepository.save(user);
        return UserDtoConverter.convertUserToDTO(user);
    }

    public void deleteBookFromUser(Long userId, Long bookId) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        Book book = bookRepository.findById(bookId).orElseThrow(BookNotFoundException::new);
        user.removeBook(book);
        userRepository.save(user);
    }
}
