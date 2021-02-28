package book.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BookResponse {
    private int bookId;
    private String title;
    private String author;

    public BookResponse(int bookId, BookRequest bookRequest) {
        this.bookId = bookId;
        this.title = bookRequest.getTitle();
        this.author = bookRequest.getAuthor();
    }
}
