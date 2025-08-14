package patricio.santamaria.productsoperator.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ValidateStockDto {
    @JsonProperty("id_product")
    private Long idProduct;
    @JsonProperty("quantity")
    private Integer quantity;
}
