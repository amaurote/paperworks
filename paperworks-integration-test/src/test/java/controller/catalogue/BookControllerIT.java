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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = PaperworksApplication.class)
@TestPropertySource(locations = "classpath:application-test.properties")
@AutoConfigureMockMvc
public class BookControllerIT {

    @Autowired
    private MockMvc mvc;

    @Test
    @Sql("classpath:/scripts/catalogue/catalogue.sql")
    public void getBookByCatalogueId() throws Exception {
        mvc.perform(get("/cat/123456789"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.catalogueIdPretty").value("123-456-789"))
                .andExpect(jsonPath("$.title").value("Sample Book"))
                .andExpect(jsonPath("$.publisher.id").value(1))
                .andDo(MockMvcResultHandlers.print());

    }

}
