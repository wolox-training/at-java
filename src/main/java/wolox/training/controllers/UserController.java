package wolox.training.controllers;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import wolox.training.exceptions.BookNotFoundException;
import wolox.training.exceptions.UserIdMismatchException;
import wolox.training.exceptions.UserNotFoundException;
import wolox.training.models.Book;
import wolox.training.models.User;
import wolox.training.repositories.BookRepository;
import wolox.training.repositories.UserRepository;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;

    @GetMapping("/{username}")
    @ApiOperation(value = "It retrieves a user given a user name")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Returns a user", response = User.class),
            @ApiResponse(code = 404, message = "User was not found")
    })
    public User findByName(@PathVariable String username) {
        return userRepository
                .findByUsername(username)
                .orElseThrow(UserNotFoundException::new);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Creates a new user")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "User created", response = User.class),
            @ApiResponse(code = 400, message = "There was a problem with the recieved data")
    })
    public User create(@RequestBody User user) {
        return userRepository.save(user);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Receives an ID and a user to update", response = User.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, response = User.class, message = "The user was found and updated"),
            @ApiResponse(code = 400, message = "The user id to update does not match the user id sent with data"),
            @ApiResponse(code = 404, message = "User not found")
    })
    public User update(@RequestBody User user, @PathVariable Long id) {
        if (user.getId() != id) {
            throw new UserIdMismatchException();
        }
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException();
        }
        return userRepository.save(user);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Given an ID deletes a user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The user was deleted successfully"),
            @ApiResponse(code = 404, message = "The user was not found"),
            @ApiResponse(code = 400, message = "The user id to update does not match the user id sent with data")
    })
    public void delete(@PathVariable Long id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException();
        }
        userRepository.deleteById(id);
    }

    @PostMapping("/{id}/books/{bookId}")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Adds a book to a user's library")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "The book was added to user's library"),
            @ApiResponse(code = 404, message = "The book or the user were not found"),
            @ApiResponse(code = 409, message = "The user already owns the book")
    })
    public User addBook(@PathVariable Long id, @PathVariable Long bookId) {
        User user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        Book book = bookRepository.findById(bookId).orElseThrow(BookNotFoundException::new);

        user.addBook(book);
        return userRepository.save(user);
    }

    @DeleteMapping("/{id}/books/{bookId}")
    @ApiOperation(value = "Removes a book from a user's library")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The book was removed from user's library"),
            @ApiResponse(code = 404, message = "The book or the user were not found")
    })
    public void deleteBook(@PathVariable Long id, @PathVariable Long bookId) {
        User user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        Book book = bookRepository.findById(bookId).orElseThrow(BookNotFoundException::new);

        user.removeBook(book);
        userRepository.save(user);
    }
}
