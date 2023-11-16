package dev.aleixmorgadas.bcnengmodelingproduct.domain.product;

import com.jayway.jsonpath.JsonPath;
import dev.aleixmorgadas.bcnengmodelingproduct.AbstractIntegrationTest;
import dev.aleixmorgadas.bcnengmodelingproduct.domain.category.Category;
import dev.aleixmorgadas.bcnengmodelingproduct.domain.category.CategoryRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ProductControllerTest extends AbstractIntegrationTest {
    Category activeCategory;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    ProductRepository productRepository;

    @BeforeAll
    void setUp() {
        activeCategory = Category.of("Books");
        categoryRepository.save(activeCategory);
    }

    @Test
    void createsAProduct() throws Exception {
        var request = new ProductController.Requests.CreateProduct("The Lord of the Rings", activeCategory.getId());
        var response = mockMvc.perform(post(ProductController.URI)
                        .content(objectMapper.writeValueAsString(request))
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.name").value("The Lord of the Rings"))
                .andExpect(jsonPath("$.categoryId").value(activeCategory.getId()));

        var id = JsonPath.parse(response.andReturn().getResponse().getContentAsString()).read("$.id", Long.class);
        var product = productRepository.findById(id).orElseThrow(() -> new AssertionError("Product not found"));
        assertThat(product.getName()).isEqualTo("The Lord of the Rings");
        assertThat(product.getCategoryId()).isEqualTo(activeCategory.getId());
    }

    @Test
    void needsAnActiveCategoryToCreateAProduct() throws Exception {
        var inactiveCategory = Category.of("Books", false);
        categoryRepository.save(inactiveCategory);

        var request = new ProductController.Requests.CreateProduct("The Lord of the Rings", inactiveCategory.getId());
        mockMvc.perform(post(ProductController.URI)
                        .content(objectMapper.writeValueAsString(request))
                        .contentType("application/json"))
                .andExpect(status().isBadRequest());
    }
}
