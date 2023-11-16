package dev.aleixmorgadas.bcnengmodelingproduct.domain.category;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(CategoryController.URI)
@AllArgsConstructor
public class CategoryController {
    public static final String URI = "/categories";
    private final CategoryRepository categoryRepository;

    @PostMapping
    public ResponseEntity<Category> createCategory(
            @RequestBody Requests.CreateCategory request
    ) {
        var category = Category.of(request.name());
        categoryRepository.save(category);
        return ResponseEntity.ok(category);
    }

    public static class Requests {
        public record CreateCategory(String name) {}
    }
}
