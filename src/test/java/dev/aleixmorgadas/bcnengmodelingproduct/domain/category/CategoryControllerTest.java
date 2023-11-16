package dev.aleixmorgadas.bcnengmodelingproduct.domain.category;

import com.jayway.jsonpath.JsonPath;
import dev.aleixmorgadas.bcnengmodelingproduct.AbstractIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CategoryControllerTest extends AbstractIntegrationTest {
    @Autowired
    CategoryRepository repository;

    @Test
    void createsCategory() throws Exception {
        var response = mockMvc.perform(post("/categories")
                        .content("""
                                {
                                    "name": "Electronics"
                                }
                                """)
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andReturn();
        var id = JsonPath.parse(response.getResponse().getContentAsString()).read("$.id", Long.class);

        var category = repository.findById(id).orElseThrow(() -> new AssertionError("Category not found"));
        assertThat(category.getName()).isEqualTo("Electronics");
        assertThat(category.isActive()).isTrue();
    }
}
