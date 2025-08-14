package patricio.santamaria.productsoperator.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class ShoppingDto {
    @JsonProperty("id_shopping_cart")
    private Long idShoppingCart;
    @JsonProperty("client")
    private String client;
    @JsonProperty("description")
    private String description;
    @JsonProperty("items")
    private List<ItemDto> items;
}
