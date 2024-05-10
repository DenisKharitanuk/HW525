package steps.asertsResponses;

import entity.Book;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class NegativeDbAssert {
    public static void dbVerifyBodyNegative(List<Book> books) {
        assertEquals(books.size(),0);
    }
}
