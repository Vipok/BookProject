package book.controller;

import book.exceptions.CreateErrorException;
import book.model.BookRequest;
import book.model.BookResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import book.service.BookService;

import java.util.List;

@RestController
@ControllerAdvice
@RequestMapping("/api/book")
public class BookController {

    @Autowired
    BookService bookService;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public BookResponse add(@RequestBody BookRequest bookRequest) throws CreateErrorException {
        return this.bookService.addBook(bookRequest);
    }

    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    public List<BookResponse> getBooks() throws CreateErrorException {
        return this.bookService.getBooks();
    }

    @RequestMapping(value = "/{bookId}", method = RequestMethod.GET)
    public BookResponse getBooks(@PathVariable(value = "bookId") Integer bookId) throws CreateErrorException {
        return this.bookService.getBook(bookId);
    }

    @RequestMapping(value = "/{bookId}", method = RequestMethod.PUT)
    public void updateBook(@RequestBody BookRequest bookRequest, @PathVariable Integer bookId) {
        bookService.editBook(bookId, bookRequest);
    }

    @RequestMapping(value = "/{bookId}", method = RequestMethod.DELETE)
    public void deleteBook(@PathVariable(value = "bookId") Integer bookId) throws CreateErrorException {
        bookService.deleteBook(bookId);
    }
}
