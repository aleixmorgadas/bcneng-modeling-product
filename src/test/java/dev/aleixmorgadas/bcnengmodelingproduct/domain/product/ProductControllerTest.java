package dev.aleixmorgadas.bcnengmodelingproduct.domain.product;

import dev.aleixmorgadas.bcnengmodelingproduct.AbstractIntegrationTest;
import dev.aleixmorgadas.bcnengmodelingproduct.domain.category.Category;
import dev.aleixmorgadas.bcnengmodelingproduct.domain.category.CategoryRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ProductControllerTest extends AbstractIntegrationTest {
    Category activeCategory;
    @Autowired
    CategoryRepository categoryRepository;

    @BeforeAll
    void setUp() {
        activeCategory = Category.of("Books");
        categoryRepository.save(activeCategory);
    }

    @Test
    void createsAProduct() throws Exception {
        mockMvc.perform(post(ProductController.URI)
                        .content("{\"name\": \"The Lord of the Rings\", \"categoryId\": " + activeCategory.getId() + "}")
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.name").value("The Lord of the Rings"))
                .andExpect(jsonPath("$.categoryId").value(activeCategory.getId()));
    }
}
