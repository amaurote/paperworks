package controller.catalogue;

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
@Sql("classpath:scripts/catalogue/category.sql")
@Transactional
public class CategoryControllerIT {

    @Autowired
    private MockMvc mvc;

    @Test
    public void getCategoryById() throws Exception {
        mvc.perform(get("/cat/categories/2"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.name").value("fiction"))
                .andExpect(jsonPath("$.caption").value("Fiction"))
                .andExpect(jsonPath("$.parent", hasEntry("1", "books")));

        mvc.perform(get("/cat/categories/1"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.name").value("books"))
                .andExpect(jsonPath("$.caption").value("Books"))
                .andExpect(jsonPath("$.parent").value(nullValue()));
    }

    @Test
    public void getCategoryPathMap() throws Exception {
        mvc.perform(get("/cat/categories/path-map/9"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().json("{\"1\":\"Books\",\"3\":\"Non-fiction\",\"4\":\"History\",\"9\":\"Second World War\"}"));
    }

    @Test
    public void getCategoryTree() throws Exception {
        var result = mvc.perform(get("/cat/categories/tree"))
                .andExpect(status().is2xxSuccessful())
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
