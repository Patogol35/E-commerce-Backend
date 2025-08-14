package patricio.santamaria.productsoperator.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "shopping_carts")
@Data
public class ShoppingCart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Item> items;

    private Integer status;

    private String statusDescription;

    private String client;

    private BigDecimal total;

    private String description;

    private Date createdAt;

    @PrePersist
    private void prePersist() {
        createdAt = new Date();
        status = 0;
        statusDescription = "SHOPPING_CART_CREATED";
    }

}
