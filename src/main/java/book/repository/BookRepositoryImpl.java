package book.repository;

import book.exceptions.CreateErrorException;
import book.model.AuthInfo;
import book.model.BookRequest;
import book.model.BookResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOError;
import java.io.IOException;
import java.util.List;
import java.util.Properties;


@Repository
@Slf4j
public class BookRepositoryImpl implements BookRepository {

    private static final String SQL_CREATE_BOOK = "INSERT INTO BOOKS(BOOK_ID, TITLE, AUTHOR) " +
            "VALUES(NEXTVAL('BOOK_ID_SEQ'), ?, ?)";

    private static final String SQL_CHECK_DUPLICATE_BOOK = "SELECT COUNT(*) FROM BOOKS WHERE TITLE = ? AND AUTHOR = ?";

    private static final String SQL_FIND_ID_BOOK_BY_TITLE_AND_AUTHOR = "SELECT BOOK_ID FROM BOOKS WHERE TITLE = ? AND AUTHOR = ?";

    private static final String SQL_GET_BOOK_BY_BOOK_ID = "SELECT BOOK_ID, TITLE, AUTHOR FROM BOOKS WHERE BOOK_ID = ?";

    private static final String SQL_GET_ALL_BOOK = "SELECT * FROM BOOKS";

    private static final String SQL_DELETE_BOOK_BY_ID = "DELETE FROM BOOKS WHERE BOOK_ID = ?";

    private static final String SQL_UPDATE_BOOK = "UPDATE BOOKS SET TITLE = ?, AUTHOR = ? WHERE BOOK_ID = ?";


    private static final String path = "src/main/resources/config.properties";

    private FileInputStream fis;

    public Properties properties = new Properties();

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public Integer checkDuplicate(BookRequest bookRequest) {
        return jdbcTemplate.queryForObject(SQL_CHECK_DUPLICATE_BOOK, Integer.class, bookRequest.getTitle(), bookRequest.getAuthor());
    }

    @Override
    public BookResponse addBook(BookRequest bookRequest) {
        jdbcTemplate.update(SQL_CREATE_BOOK, bookRequest.getTitle(), bookRequest.getAuthor());
        return new BookResponse(findIdByTitleAndAuthor(bookRequest), bookRequest);
    }

    @Override
    public Integer findIdByTitleAndAuthor(BookRequest bookRequest) {
        return jdbcTemplate.queryForObject(SQL_FIND_ID_BOOK_BY_TITLE_AND_AUTHOR, Integer.class, bookRequest.getTitle(), bookRequest.getAuthor());
    }

    @Override
    public List<BookResponse> getBooks() {
        return jdbcTemplate.query(SQL_GET_ALL_BOOK, (rs, rowNum) ->
                new BookResponse(rs.getInt("book_id"),
                        rs.getString("title"),
                        rs.getString("author")));
    }

    @Override
    public BookResponse getBook(Integer bookId) {
        return jdbcTemplate.queryForObject(SQL_GET_BOOK_BY_BOOK_ID, new Object[]{bookId}, (rs, rowNum) ->
                new BookResponse(rs.getInt("book_id"),
                        rs.getString("title"),
                        rs.getString("author")));
    }

    @Override
    public void deleteBook(Integer bookId) {
        jdbcTemplate.update(SQL_DELETE_BOOK_BY_ID, bookId);
    }

    @Override
    public void updateBook(Integer bookId, BookRequest bookRequest) {
        jdbcTemplate.update(SQL_UPDATE_BOOK, bookRequest.getTitle(), bookRequest.getAuthor(), bookId);
    }

    @Override
    public AuthInfo getAuthInfo() {
        AuthInfo authInfo = new AuthInfo();
        try {
            fis = new FileInputStream(path);
            properties.load(fis);
            authInfo.setUser(properties.getProperty("user"));
            authInfo.setPassword(properties.getProperty("password"));
            fis.close();
            return authInfo;
        } catch (IOException e) {
            e.getMessage();
            log.error("Error: the property file is missing");
        }
        return authInfo;
    }
}
