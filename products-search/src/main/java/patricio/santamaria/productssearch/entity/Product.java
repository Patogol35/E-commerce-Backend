package patricio.santamaria.productssearch.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "products")
@Data
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String sortDescription;

    private String longDescription;

    private BigDecimal price;

    private boolean active;

    private Integer stock;

    private Date createdAt;

    private Date updatedAt;

    private Integer discount;

    private Double rating;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "products_categories",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    private List<Category> categories;

    @PrePersist
    private void prePersist() {
        createdAt = new Date();
        updatedAt = new Date();
        discount = Math.random() > 0.5 ? 10 : 0;
        active = true;
        stock = (int) (Math.random() * 100);
        rating = Math.random() * 5;
    }

    @PreUpdate
    private void preUpdate() {
        updatedAt = new Date();
    }

}
