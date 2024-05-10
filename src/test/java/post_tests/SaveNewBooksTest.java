package post_tests;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import models.negative_responses.NegativeResponses;
import models.positive_responses.SaveNewAuthorPositiveResponse;
import models.positive_responses.SaveNewBooksPositiveResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import repository.BookRepository;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static steps.Specifications.*;
import static steps.asertsResponses.DbAssert.dbBookVerification;
import static steps.asertsResponses.NegativeAssert.verifyBodyNegative;
import static steps.asertsResponses.NegativeDbAssert.dbVerifyBodyNegative;
import static steps.asertsResponses.SaveBookAssert.verifyBodySaveBook;
import static utils.DateGenerator.dateGenerator;

@Epic("PostTests")
@Story("saveNewBook")
public class SaveNewBooksTest {

    BookRepository bookDB = new BookRepository();


    @DisplayName("Сохранение книги ")
    @Description("Книга сохранена в базе данных , ответ содержит id сохраненной  книги , книга с id присутствует в базе данных, статус код 201")
    @Test
    public void saveBookTest() {
        SaveNewAuthorPositiveResponse author = requestSpecSaveNewAuthor(randomAlphabetic(5),
                randomAlphabetic(5), randomAlphabetic(5), 201, dateGenerator());
        long id = author.getAuthorId();

        String bookTitle = randomAlphabetic(5);
        SaveNewBooksPositiveResponse book = requestSpecSaveNewBook(bookTitle, id, 201);

        long bookId = book.getBookId();
        verifyBodySaveBook(book, bookId);

        dbBookVerification(bookId, bookDB.findBookById(bookId));
    }


    @DisplayName("Сохранение книги с неизвестным id автора")
    @Description("Книга не сохранена , статус код 409, ошибка 1004")
    @Test
    public void saveBookUnknownAuthorTest() {
        long authorId = 666;
        NegativeResponses response = requestSpecSaveNewBookNegative(randomAlphabetic(5), authorId, 409);
        verifyBodyNegative(response, "1004", "Указанный автор не существует в таблице");
        dbVerifyBodyNegative(bookDB.findBookByAuthorId(authorId));
    }

    @DisplayName("Сохранение книги с отрицательным id автора")
    @Description("Книга не сохранена , статус код 409, ошибка 1004")
    @ParameterizedTest(name = "id = {0}")
    @ValueSource(longs = {-1, -2,})
    public void saveBookNegativeId(long authorId) {
        NegativeResponses response = requestSpecSaveNewBookNegative(randomAlphabetic(5), (authorId), 409);
        verifyBodyNegative(response, "1004", "Указанный автор не существует в таблице");
        dbVerifyBodyNegative(bookDB.findBookByAuthorId(authorId));
    }

    @DisplayName("Сохранение книги с пустым полем bookTitle")
    @Description("Книга не сохранена , статус код 400, ошибка 1001")
    @ParameterizedTest
    @NullSource
    public void saveBookNullTitle(String bookTitle) {
        NegativeResponses response = requestSpecSaveNewBookNegative(bookTitle, 1, 400);
        verifyBodyNegative(response, "1001", "Не передан обязательный параметр: bookTitle");
        dbVerifyBodyNegative(bookDB.findBookByTitle(null));
    }
}

