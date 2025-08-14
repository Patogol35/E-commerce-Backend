package patricio.santamaria.productsoperator.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ItemDto {
    @JsonProperty("id_product")
    private Long idProduct;
    @JsonProperty("price")
    private BigDecimal price;
    @JsonProperty("quantity")
    private Integer quantity;
    @JsonProperty("product")
    private String product;
}
