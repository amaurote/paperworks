package controller.customer;

import com.amaurote.PaperworksApplication;
import com.amaurote.catalogue.service.BookService;
import com.amaurote.social.service.RatingService;
import com.amaurote.social.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = PaperworksApplication.class)
@TestPropertySource(locations = "classpath:application-test.properties")
@AutoConfigureMockMvc
@Sql(scripts = {
        "classpath:/scripts/catalogue/catalogue.sql",
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
    private MockMvc mvc;

    @Test
    @WithMockUser(username = "simonej")
    public void getUserBookRating() throws Exception {
        mvc.perform(get("/customer/rating")
                        .param("book", "123456789"))
                .andExpect(status().isOk())
                .andExpect(content().string("5"));
    }

    @Test
    @WithMockUser(username = "sokolova1")
    public void rateOrUpdate() throws Exception {
        var user = userService.getUserByUsername("sokolova1");
        var book = bookService.getBookByCatalogueNumber(123456789L);

        mvc.perform(put("/customer/rating")
                        .param("book", "123456789")
                        .param("value", "2"))
                .andExpect(status().isOk());

        var score = ratingService.getUserBookRating(book, user);
        assertEquals(2, score);

        mvc.perform(put("/customer/rating")
                        .param("book", "123456789")
                        .param("value", "4"))
                .andExpect(status().isOk());

        score = ratingService.getUserBookRating(book, user);
        assertEquals(4, score);

        mvc.perform(put("/customer/rating")
                        .param("book", "123456789")
                        .param("value", "0"))
                .andExpect(status().isOk());

        score = ratingService.getUserBookRating(book, user);
        assertNull(score);
    }

    @Test
    @WithMockUser(username = "simonej")
    public void deleteRating() throws Exception {
        var user = userService.getUserByUsername("simonej");
        var book = bookService.getBookByCatalogueNumber(123456789L);
        var score = ratingService.getUserBookRating(book, user);
        assertEquals(5, score);

        mvc.perform(delete("/customer/rating")
                        .param("book", "123456789"))
                .andExpect(status().isOk());

        score = ratingService.getUserBookRating(book, user);
        assertNull(score);
    }

}
