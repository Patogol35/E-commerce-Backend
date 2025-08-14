package patricio.santamaria.productssearch.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ValidateStockDto {
    @JsonProperty("id_product")
    private Long idProduct;
    @JsonProperty("quantity")
    private Integer quantity;
}
