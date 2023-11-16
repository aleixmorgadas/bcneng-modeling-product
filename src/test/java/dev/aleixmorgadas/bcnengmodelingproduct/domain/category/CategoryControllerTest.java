package dev.aleixmorgadas.bcnengmodelingproduct.domain.category;

import com.jayway.jsonpath.JsonPath;
import dev.aleixmorgadas.bcnengmodelingproduct.AbstractIntegrationTest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CategoryControllerTest extends AbstractIntegrationTest {
    @Autowired
    CategoryRepository repository;

    Category booksCategory;

    @BeforeAll
    void setup() {
        booksCategory = Category.of("Books");
        repository.save(booksCategory);
    }

    @AfterAll
    void tearDown() {
        repository.deleteAll();
    }

    @Test
    void createsCategory() throws Exception {
        var request = new CategoryController.Requests.CreateCategory("Electronics");
        var response = mockMvc.perform(post("/categories")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andReturn();
        var id = JsonPath.parse(response.getResponse().getContentAsString()).read("$.id", Long.class);

        var category = repository.findById(id).orElseThrow(() -> new AssertionError("Category not found"));
        assertThat(category.getName()).isEqualTo("Electronics");
        assertThat(category.isActive()).isTrue();
    }

    @Test
    void receiveCategory() throws Exception {
        mockMvc.perform(get(CategoryController.URI + "/%s".formatted(booksCategory.getId())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(booksCategory.getId()))
                .andExpect(jsonPath("$.name").value(booksCategory.getName()))
                .andExpect(jsonPath("$.active").value(booksCategory.isActive()));
    }
}
