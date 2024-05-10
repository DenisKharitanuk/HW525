package steps.asertsResponses;

import entity.Author;
import entity.Book;
import models.positive_responses.GetAllAuthorsBooksPositiveResponse;
import models.positive_responses.SaveNewAuthorPositiveResponse;
import models.positive_responses.SaveNewBooksPositiveResponse;
import repository.AuthorRepository;
import repository.BookRepository;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DbAssert {

    public static void sizeVerification(int size, List<Book> listOfBooks) {
        assertEquals(size, listOfBooks.size());
    }

    public static void dbBookVerification(long bookID, List<Book> books) {
        assertEquals(books.getFirst().getId(), bookID);
    }

    public static void dbAuthorVerification(long authorId, List<Book> books) {
        assertEquals(books.getFirst().getId(), authorId);
    }

    public static void dbVerifyGetBook(List<Book> books, String bookTitle, int bookIndex, Date updated, long authorId) {
        assertEquals(books.get(bookIndex).getBookTitle(), bookTitle);
        assertEquals(books.get(bookIndex).getAuthorId(), authorId);

        Date date = books.get(bookIndex).getUpdated();
        TimeZone.setDefault(TimeZone.getTimeZone("GMT"));
        SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        assertEquals(formater.format(date), formater.format(updated));
    }

    public static void dbVerifyGetBooks(BookRepository books, long authorId, List<String> bookTitles) {
        int i = 0;
        for (String bookTitle : bookTitles) {
            assertEquals(bookTitle, books.findBookByAuthorId(authorId).get(i).getBookTitle());
            i++;
        }
    }

    public static void dbVerifyBodyGetEmptyBookList(List<Book> books) {
        assertEquals(books.size(), 0);
    }

}

