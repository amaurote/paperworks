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

import static org.hamcrest.Matchers.hasValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = PaperworksApplication.class)
@TestPropertySource(locations = "classpath:application-test.properties")
@AutoConfigureMockMvc
@Transactional
public class BookControllerIT {

    @Autowired
    private MockMvc mvc;

    @Test
    @Sql("classpath:/scripts/catalogue/catalogue.sql")
    public void getBookByCatalogueId_success() throws Exception {
        mvc.perform(get("/cat/12-34-56-78-9"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.catalogueIdPretty").value("123-456-789"))
                .andExpect(jsonPath("$.title").value("Sample Book"))
                .andExpect(jsonPath("$.publisher", hasValue("Sample Publisher")));
//                .andDo(MockMvcResultHandlers.print());

    }

    @Test
    public void getBookByCatalogueId_badRequest() throws Exception {
        mvc.perform(get("/cat/abcd-efgh"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void search() {
        // todo
    }

}
