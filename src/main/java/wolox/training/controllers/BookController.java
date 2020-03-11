package wolox.training.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import wolox.training.dto.BookDTO;
import wolox.training.exceptions.BookIdMismatchException;
import wolox.training.models.Book;
import wolox.training.repositories.BookRepository;
import wolox.training.services.BookService;

@RestController
@RequestMapping("/api/books")
@Api
public class BookController {

    @Autowired
    BookService bookService;

    @GetMapping("/{author}")
    @ApiOperation(value = "Given an author returns the book", response = Book.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, response = Book.class, message = "Returns the first book for that author"),
            @ApiResponse(code = 404, message = "The book was not found")
    })
    public BookDTO findByAuthor(
            @ApiParam(value = "The author's name", required = true)
            @PathVariable String author
    ) {
        return bookService.findByAuthor(author);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Recieves a book to create", response = Book.class)
    @ApiResponses(value={
            @ApiResponse(code = 201, response = Book.class, message = "The book was created"),
            @ApiResponse(code = 400, message = "There was a problem with the recieved data")
    })
    public BookDTO create(
            @ApiParam(value = "The book to be created.", required = true)
            @RequestBody BookDTO bookDTO
    ) {
        return bookService.createBook(bookDTO);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Receives an ID and a book to update", response = Book.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, response = Book.class, message = "The book was found and updated"),
            @ApiResponse(code = 400, message = "The book id to update does not match the book id sent with data"),
            @ApiResponse(code = 404, message = "Book not found")
    })
    public BookDTO update(
            @ApiParam(value = "The book to be updated", required = true)
            @RequestBody BookDTO bookDTO,
            @ApiParam(value = "The book ID", required = true, example = "0")
            @PathVariable Long id
    ) {
        if (!id.equals(bookDTO.getId())) {
            throw new BookIdMismatchException();
        }

        if (!bookService.bookExistsById(id)) {
            return bookService.createBook(bookDTO);
        }

        return bookService.updateBook(bookDTO);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Given an ID deletes a book")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The book was deleted successfully"),
            @ApiResponse(code = 404, message = "The book was not found"),
            @ApiResponse(code = 400, message = "The book id to update does not match the book id sent with data")
    })
    public void delete(
            @ApiParam(value = "The book ID", required = true)
            @PathVariable Long id
    ) {
        bookService.deleteBook(id);
    }

    @GetMapping("/greeting")
    @ApiOperation(value = "Given a name it greets a person.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Says hi!")
    })
    public String greeting(
            @ApiParam(value = "A person's first name.")
            @RequestParam(name="name", required=false, defaultValue="World") String name,
            Model model
    ) {
        model.addAttribute("name", name);
        return "greeting";
    }
}
