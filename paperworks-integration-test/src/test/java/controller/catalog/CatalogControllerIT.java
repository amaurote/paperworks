package controller.catalog;

import com.amaurote.PaperworksApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = PaperworksApplication.class)
@TestPropertySource(locations = "classpath:application-test.properties")
@AutoConfigureMockMvc
@Sql(scripts = {
        "classpath:/scripts/catalog/catalog.sql",
        "classpath:/scripts/social/users.sql",
        "classpath:/scripts/social/social.sql",
        "classpath:scripts/catalog/category.sql"})
@Transactional
public class CatalogControllerIT {

    @Autowired
    private MockMvc mvc;

    @Test
    public void getBookByCatalogId_success() throws Exception {
        mvc.perform(get("/catalog/12-34-56-78-9"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.catalogIdPretty").value("123-456-789"))
                .andExpect(jsonPath("$.title").value("Sample Book"))
                .andExpect(jsonPath("$.publisher", hasValue("Sample Publisher")));
//                .andDo(MockMvcResultHandlers.print());

    }

    @Test
    public void getBookByCatalogId_badRequest() throws Exception {
        mvc.perform(get("/catalog/abcd-efgh"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void search() {
        // todo
    }

    @Test
    public void getBookRating() throws Exception {
        mvc.perform(get("/catalog/123456789/rating"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.rating").value(3.25))
                .andExpect(jsonPath("$.totalRatings").value(4));
    }

    @Test
    public void getBookReviews() throws Exception {
        mvc.perform(get("/catalog/123456789/reviews"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("[0].text").value("This is a great book"))
                .andExpect(jsonPath("[0].reviewerUsername").value("simonej"))
                .andExpect(jsonPath("[0].dateCreated").isNotEmpty())
                .andExpect(jsonPath("[1].text").value("This is not a good book"))
                .andExpect(jsonPath("[1].reviewerUsername").value("cain12"))
                .andExpect(jsonPath("[1].dateCreated").isNotEmpty());
    }

    @Test
    public void getAllLanguages() throws Exception {
        mvc.perform(get("/catalog/languages"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("[0].code").value("en"))
                .andExpect(jsonPath("[0].language").value("english"))
                .andExpect(jsonPath("[1].code").value("sk"))
                .andExpect(jsonPath("[1].language").value("slovak"));
    }

    @Test
    public void getCategoryById() throws Exception {
        mvc.perform(get("/catalog/categories/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("fiction"))
                .andExpect(jsonPath("$.caption").value("Fiction"))
                .andExpect(jsonPath("$.parent", hasEntry("1", "books")));

        mvc.perform(get("/catalog/categories/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("books"))
                .andExpect(jsonPath("$.caption").value("Books"))
                .andExpect(jsonPath("$.parent").value(nullValue()));
    }

    @Test
    public void getCategoryPathMap() throws Exception {
        mvc.perform(get("/catalog/categories/9/path-map"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"1\":\"Books\",\"3\":\"Non-fiction\",\"4\":\"History\",\"9\":\"Second World War\"}"));
    }

    @Test
    public void getCategoryTree() throws Exception {
        var result = mvc.perform(get("/catalog/categories/tree"))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        var content = result.getResponse().getContentAsString();
        assertEquals("""
                books
                \tfiction
                \tnon_fiction
                \t\thistory
                \t\t\tegypt
                \t\t\tsecond_ww
                \t\tscience
                \t\tcooking
                \t\t\tvegan""", content.trim());
    }

}
