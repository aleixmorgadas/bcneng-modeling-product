package dev.aleixmorgadas.bcnengmodelingproduct.domain.product;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import org.hibernate.annotations.Immutable;

@Getter
@Entity
@Immutable
@Table(name = "product_view")
public class ProductView {
    @Id
    private Long id;
    private String name;
    private String category;
}
