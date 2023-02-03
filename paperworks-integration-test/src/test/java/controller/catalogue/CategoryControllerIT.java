package controller.catalogue;

import com.amaurote.PaperworksApplication;
import com.amaurote.catalogue.service.BookService;
import com.amaurote.catalogue.service.CategoryService;
import com.amaurote.domain.entity.BookCategory;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = PaperworksApplication.class)
@TestPropertySource(locations = "classpath:application-test.properties")
@AutoConfigureMockMvc
@Sql(scripts = {
        "classpath:scripts/catalogue/catalogue.sql",
        "classpath:scripts/catalogue/category.sql"})
@Transactional
public class CategoryControllerIT {

    @Autowired
    private BookService bookService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private MockMvc mvc;

    private final long BOOK_CAT_ID = 123456789;

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

    @Test
    public void assignCategory() throws Exception {
        mvc.perform(put("/cat/categories/assign/" + BOOK_CAT_ID)
                        .param("category", "9")
                        .param("isMain", "true"))
                .andExpect(status().is2xxSuccessful());

        var book = bookService.getBookByCatalogueNumber(BOOK_CAT_ID);
        assertNotNull(book);

        var categories = book.getCategories();
        assertEquals(3, categories.size());

        var main = categories.stream().filter(BookCategory::isMainCategory).toList();
        assertEquals(1, main.size());
        assertEquals("second_ww", main.get(0).getCategory().getName());
    }

    @Test
    public void unassignCategory() throws Exception {
        mvc.perform(post("/cat/categories/unassign/" + BOOK_CAT_ID)
                        .param("category", "2"))
                .andExpect(status().is2xxSuccessful());

        var book = bookService.getBookByCatalogueNumber(BOOK_CAT_ID);
        assertNotNull(book);

        assertEquals(1, book.getCategories().size());
        assertEquals("science", book.getCategories().get(0).getCategory().getName());
    }

    @Test
    public void unassignAllCategories() throws Exception {
        mvc.perform(post("/cat/categories/unassign-all/" + BOOK_CAT_ID))
                .andExpect(status().is2xxSuccessful());

        var book = bookService.getBookByCatalogueNumber(BOOK_CAT_ID);

        assertNotNull(book);
        assertTrue(book.getCategories().isEmpty());
    }

    @Test
    public void createSingleCategory() throws Exception {
        var requestBody = new CategoryService.CategoryCreateRequestDTO("test_1", "First Test", 9L);

        mvc.perform(post("/cat/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(requestBody)))
                .andExpect(status().is2xxSuccessful());

        var result = categoryService.getChildCategories(9L);
        assertEquals(1, result.size());
        assertEquals("test_1", result.get(0).getName());
    }

    @Test
    public void buildCategoryPath() throws Exception {
        var path = "test_first.second.third";

        mvc.perform(put("/cat/categories/build-path")
                        .contentType(MediaType.TEXT_PLAIN)
                        .content(path))
                .andExpect(status().is2xxSuccessful());

        // todo
    }

    private static String toJson(Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
