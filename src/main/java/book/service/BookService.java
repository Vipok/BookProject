package book.service;

import book.exceptions.CreateErrorException;
import book.model.AuthInfo;
import book.model.BookRequest;
import book.model.BookResponse;

import java.util.List;

public interface BookService {
    BookResponse addBook(BookRequest bookRequest) throws CreateErrorException;

    List<BookResponse> getBooks() throws CreateErrorException;

    BookResponse getBook(Integer bookId) throws CreateErrorException;

    void editBook(Integer bookId, BookRequest bookRequest);

    void deleteBook(Integer bookId) throws CreateErrorException;

    AuthInfo getAuthInfo();
}
