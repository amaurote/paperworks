package controller.customer;

import com.amaurote.PaperworksApplication;
import com.amaurote.catalog.service.BookService;
import com.amaurote.social.service.RatingService;
import com.amaurote.social.service.ReviewService;
import com.amaurote.social.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = PaperworksApplication.class)
@TestPropertySource(locations = "classpath:application-test.properties")
@AutoConfigureMockMvc
@Sql(scripts = {
        "classpath:/scripts/catalog/catalog.sql",
        "classpath:/scripts/social/users.sql",
        "classpath:/scripts/social/social.sql"})
@Transactional
public class CustomerControllerIT {

    @Autowired
    private UserService userService;

    @Autowired
    private BookService bookService;

    @Autowired
    private RatingService ratingService;

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private MockMvc mvc;

    private final long BOOK_CAT_ID = 123456789L;

    private final String BOOK_CAT_ID_STR = "123456789";

    @Test
    @WithMockUser(username = "simonej")
    public void getUserBookRating() throws Exception {
        mvc.perform(get("/customer/rating")
                        .param("book", BOOK_CAT_ID_STR))
                .andExpect(status().isOk())
                .andExpect(content().string("5"));
    }

    @Test
    @WithMockUser(username = "sokolova1")
    public void rateOrUpdate() throws Exception {
        var user = userService.getUserByUsername("sokolova1");
        var book = bookService.getBookByCatalogNumber(123456789L);

        mvc.perform(put("/customer/rating")
                        .param("book", BOOK_CAT_ID_STR)
                        .param("value", "2"))
                .andExpect(status().isOk());

        var score = ratingService.getUserBookRating(book, user);
        assertEquals(2, score);

        mvc.perform(put("/customer/rating")
                        .param("book", BOOK_CAT_ID_STR)
                        .param("value", "4"))
                .andExpect(status().isOk());

        score = ratingService.getUserBookRating(book, user);
        assertEquals(4, score);

        mvc.perform(put("/customer/rating")
                        .param("book", BOOK_CAT_ID_STR)
                        .param("value", "0"))
                .andExpect(status().isOk());

        score = ratingService.getUserBookRating(book, user);
        assertNull(score);
    }

    @Test
    @WithMockUser(username = "simonej")
    public void deleteRating() throws Exception {
        var user = userService.getUserByUsername("simonej");
        var book = bookService.getBookByCatalogNumber(BOOK_CAT_ID);
        var score = ratingService.getUserBookRating(book, user);
        assertEquals(5, score);

        mvc.perform(delete("/customer/rating")
                        .param("book", BOOK_CAT_ID_STR))
                .andExpect(status().isOk());

        score = ratingService.getUserBookRating(book, user);
        assertNull(score);
    }

    @Test
    @WithMockUser(username = "simonej")
    public void getUserBookReview() throws Exception {
        mvc.perform(get("/customer/review")
                        .param("book", BOOK_CAT_ID_STR))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.text").value("This is a great book"))
                .andExpect(jsonPath("$.reviewerUsername").value("simonej"))
                .andExpect(jsonPath("$.dateCreated").value("2023-01-01T00:00:00Z"));
    }

    @Test
    @WithMockUser(username = "simonej")
    public void reviewOrUpdate() throws Exception {
        var requestBody =
                new ReviewService.UserReviewRequestDTO(BOOK_CAT_ID_STR, "Test review");

        mvc.perform(put("/customer/review")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(requestBody)))
                .andExpect(status().is2xxSuccessful());

        var user = userService.getUserByUsername("simonej");
        var book = bookService.getBookByCatalogNumber(BOOK_CAT_ID);
        var review = reviewService.getUserBookReview(book, user);

        assertNotNull(review.getDateUpdated());
        assertEquals("Test review", review.getText());
    }

    @Test
    @WithMockUser(username = "simonej")
    public void deleteReview() throws Exception {
        mvc.perform(delete("/customer/review")
                        .param("book", BOOK_CAT_ID_STR))
                .andExpect(status().isOk());

        var user = userService.getUserByUsername("simonej");
        var book = bookService.getBookByCatalogNumber(BOOK_CAT_ID);
        var review = reviewService.getUserBookReview(book, user);

        assertNull(review);
    }

    private static String toJson(Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
