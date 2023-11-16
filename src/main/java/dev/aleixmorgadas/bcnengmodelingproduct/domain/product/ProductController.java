package dev.aleixmorgadas.bcnengmodelingproduct.domain.product;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ProductController.URI)
@AllArgsConstructor
public class ProductController {
    public static final String URI = "/products";
    private final ProductService productService;

    @PostMapping
    public ResponseEntity<Product> create(@RequestBody Requests.CreateProduct request) {
        var product = productService.create(request);
        return ResponseEntity.ok(product);
    }

    public static class Requests {
        public record CreateProduct(String name, Long categoryId) {
        }
    }
}
