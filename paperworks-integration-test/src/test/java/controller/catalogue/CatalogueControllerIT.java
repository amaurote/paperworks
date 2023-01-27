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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = PaperworksApplication.class)
@TestPropertySource(locations = "classpath:application-test.properties")
@AutoConfigureMockMvc
@Transactional
public class CatalogueControllerIT {

    @Autowired
    private MockMvc mvc;

    @Test
    @Sql("classpath:/scripts/catalogue/catalogue.sql")
    public void getAllLanguages() throws Exception {
        mvc.perform(get("/cat/languages"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("[0].code").value("en"))
                .andExpect(jsonPath("[0].language").value("english"))
                .andExpect(jsonPath("[1].code").value("sk"))
                .andExpect(jsonPath("[1].language").value("slovak"));
    }

}
