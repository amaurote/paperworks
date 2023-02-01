package controller.catalogue;

import com.amaurote.PaperworksApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = PaperworksApplication.class)
@TestPropertySource(locations = "classpath:application-test.properties")
@AutoConfigureMockMvc
@Sql(scripts = {
        "classpath:/scripts/catalogue/catalogue.sql",
        "classpath:/scripts/social/users.sql",
        "classpath:/scripts/social/social.sql"})
@Transactional
public class SocialControllerIT {

    @Autowired
    private MockMvc mvc;

    @Test
    public void getBookRating() throws Exception {
        mvc.perform(get("/cat/rating/123456789"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.rating").value(3.25))
                .andExpect(jsonPath("$.totalRatings").value(4));
    }

    @Test
    public void getBookReviews() throws Exception {
        mvc.perform(get("/cat/review/123456789"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("[0].text").value("This is a great book"))
                .andExpect(jsonPath("[0].reviewerUsername").value("hubert1"))
                .andExpect(jsonPath("[0].dateCreated").isNotEmpty())
                .andExpect(jsonPath("[1].text").value("This is not a good book"))
                .andExpect(jsonPath("[1].reviewerUsername").value("cain12"))
                .andExpect(jsonPath("[1].dateCreated").isNotEmpty());
    }

}
