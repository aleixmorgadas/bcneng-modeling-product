package dev.aleixmorgadas.bcnengmodelingproduct.domain.product;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ProductController.URI)
@AllArgsConstructor
public class ProductController {
    public static final String URI = "/products";
    private final ProductService productService;
    private final ProductViewRepository productViewRepository;

    @PostMapping
    public ResponseEntity<Product> create(@RequestBody Requests.CreateProduct request) {
        var product = productService.create(request);
        return ResponseEntity.ok(product);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductView> get(@PathVariable Long id) {
        var productView = productViewRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));
        return ResponseEntity.ok(productView);
    }

    public static class Requests {
        public record CreateProduct(String name, Long categoryId) {
        }
    }
}
