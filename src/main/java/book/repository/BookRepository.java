package book.repository;

import book.model.AuthInfo;
import book.model.BookRequest;
import book.model.BookResponse;

import java.util.List;

public interface BookRepository {
    Integer checkDuplicate(BookRequest bookRequest);

    BookResponse addBook(BookRequest bookRequest);

    Integer findIdByTitleAndAuthor(BookRequest bookRequest);

    AuthInfo getAuthInfo();

    List<BookResponse> getBooks();

    BookResponse getBook(Integer bookId);

    void deleteBook (Integer bookId);

    void updateBook(Integer bookId, BookRequest bookRequest);
}
