package dev.aleixmorgadas.bcnengmodelingproduct.domain.category;

import com.jayway.jsonpath.JsonPath;
import dev.aleixmorgadas.bcnengmodelingproduct.AbstractIntegrationTest;
import org.junit.jupiter.api.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CategoryControllerTest extends AbstractIntegrationTest {

    @Test
    void createsCategory() throws Exception {
        mockMvc.perform(post("/categories")
                        .content("""
                                {
                                    "name": "Electronics"
                                }
                                """)
                        .contentType("application/json"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andReturn();
    }
}
