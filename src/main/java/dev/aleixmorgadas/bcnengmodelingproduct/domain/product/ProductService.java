package dev.aleixmorgadas.bcnengmodelingproduct.domain.product;

import dev.aleixmorgadas.bcnengmodelingproduct.domain.category.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

@Service
@AllArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    Product create(ProductController.Requests.CreateProduct createProduct) {
        var category = categoryRepository.findById(createProduct.categoryId())
                .orElseThrow(() -> new IllegalArgumentException("Category not found"));
        if (!category.isActive()) {
            throw new InActiveCategoryException("Category is not active");
        }
        var product = Product.of(createProduct.name(), createProduct.categoryId());
        productRepository.save(product);
        return product;
    }

    @ResponseStatus(reason = "Category is not active", value = org.springframework.http.HttpStatus.BAD_REQUEST)
    public static class InActiveCategoryException extends RuntimeException {
        public InActiveCategoryException(String message) {
            super(message);
        }
    }
}
