package book.service;

import book.exceptions.CreateErrorException;
import book.model.AuthInfo;
import book.model.BookRequest;
import book.model.BookResponse;
import org.springframework.beans.factory.annotation.Autowired;
import book.repository.BookRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

@Service
@Transactional
public class BookServiceImpl implements BookService {

    @Autowired
    BookRepository bookRepository;

    @Override
    @ExceptionHandler(value = CreateErrorException.class)
    public BookResponse addBook(BookRequest bookRequest) throws CreateErrorException {
        if (bookRequest.getAuthor() == null)
            throw new CreateErrorException("The 'author' field must be filled in.", HttpStatus.BAD_REQUEST);
        if (bookRequest.getTitle() == null)
            throw new CreateErrorException("The 'title' field must be filled in.", HttpStatus.BAD_REQUEST);
        Integer count = bookRepository.checkDuplicate(bookRequest);
        if (count > 0) throw new CreateErrorException("This book has already been added", HttpStatus.BAD_REQUEST);
        return bookRepository.addBook(bookRequest);
    }

    @Override
    public List<BookResponse> getBooks() throws CreateErrorException {
        List<BookResponse> list = bookRepository.getBooks();
        if (list.isEmpty())
            throw new CreateErrorException("No data found for the query", HttpStatus.BAD_REQUEST);
        return list;
    }

    @Override
    public BookResponse getBook(Integer bookId) throws CreateErrorException {
        BookResponse bookResponse = null;
        try {
            bookResponse = bookRepository.getBook(bookId);
        } catch (Exception e) {
            e.getMessage();
        }
        if (bookResponse == null) throw
                new CreateErrorException("bookId=" + bookId + " not found in database", HttpStatus.BAD_REQUEST);
        return bookResponse;
    }

    @Override
    public void editBook(Integer bookId, BookRequest bookRequest) {
        bookRepository.updateBook(bookId, bookRequest);
    }

    @Override
    public void deleteBook(Integer bookId) throws CreateErrorException {
        getBook(bookId);
        bookRepository.deleteBook(bookId);
    }

    //можно поменять реализацию и вместо файла брать данные из постгреса
    @Override
    public AuthInfo getAuthInfo() {
        return bookRepository.getAuthInfo();
    }
}
