package patricio.santamaria.productsoperator.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "items")
@Data
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer quantity;

    private BigDecimal subtotal;

    private Long IdProduct;
    private String product;

    private Date createdAt;

    @PrePersist
    private void prePersist() {
        createdAt = new Date();
    }

}
