package controller.admin;

import com.amaurote.PaperworksApplication;
import com.amaurote.catalog.service.BookService;
import com.amaurote.catalog.service.CategoryService;
import com.amaurote.domain.entity.BookCategory;
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

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = PaperworksApplication.class)
@TestPropertySource(locations = "classpath:application-test.properties")
@AutoConfigureMockMvc
@Sql(scripts = {
        "classpath:scripts/catalog/catalog.sql",
        "classpath:scripts/catalog/category.sql",
        "classpath:/scripts/social/users.sql"})
@Transactional
public class CategoryControllerIT {

    @Autowired
    private BookService bookService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private MockMvc mvc;

    private final long BOOK_CAT_ID = 123456789L;

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void assignCategory() throws Exception {
        mvc.perform(put("/admin/categories/assign/" + BOOK_CAT_ID)
                        .param("category", "9")
                        .param("isMain", "true"))
                .andExpect(status().is2xxSuccessful());

        var book = bookService.getBookByCatalogNumber(BOOK_CAT_ID);
        assertNotNull(book);

        var categories = book.getCategories();
        assertEquals(3, categories.size());

        var main = categories.stream().filter(BookCategory::isMainCategory).toList();
        assertEquals(1, main.size());
        assertEquals("second_ww", main.get(0).getCategory().getName());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void unassignCategory() throws Exception {
        mvc.perform(post("/admin/categories/unassign/" + BOOK_CAT_ID)
                        .param("category", "2"))
                .andExpect(status().is2xxSuccessful());

        var book = bookService.getBookByCatalogNumber(BOOK_CAT_ID);
        assertNotNull(book);

        assertEquals(1, book.getCategories().size());
        assertEquals("science", book.getCategories().get(0).getCategory().getName());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void unassignAllCategories() throws Exception {
        mvc.perform(post("/admin/categories/unassign-all/" + BOOK_CAT_ID))
                .andExpect(status().is2xxSuccessful());

        var book = bookService.getBookByCatalogNumber(BOOK_CAT_ID);

        assertNotNull(book);
        assertTrue(book.getCategories().isEmpty());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void createSingleCategory() throws Exception {
        var requestBody = new CategoryService.CategoryCreateRequestDTO("test_1", "First Test", 9L);

        mvc.perform(post("/admin/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(requestBody)))
                .andExpect(status().is2xxSuccessful());

        var result = categoryService.getChildCategories(9L);
        assertEquals(1, result.size());
        assertEquals("test_1", result.get(0).getName());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void buildCategoryPath() throws Exception {
        var path = "test_first.second.third";

        mvc.perform(put("/admin/categories/build-path")
                        .contentType(MediaType.TEXT_PLAIN)
                        .content(path))
                .andExpect(status().is2xxSuccessful());

        var filtered = categoryService.getAllCategories().stream()
                .filter(category -> category.getName().equals("third")).toList();
        assertEquals(1, filtered.size());

        var pathMap = categoryService.generateCategoryPathMap(filtered.get(0).getId());
        assertTrue(pathMap.values().containsAll(List.of("third", "second", "test_first")));
    }

    private static String toJson(Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
