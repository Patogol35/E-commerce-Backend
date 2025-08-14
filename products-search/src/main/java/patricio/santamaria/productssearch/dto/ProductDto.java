package patricio.santamaria.productssearch.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductDto {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("sort_description")
    private String sortDescription;
    @JsonProperty("long_description")
    private String longDescription;
    @JsonProperty("price")
    private BigDecimal price;
    @JsonProperty("active")
    private Boolean active;
    @JsonProperty("stock")
    private Integer stock;
    @JsonProperty("discount")
    private Integer discount;
    @JsonProperty("rating")
    private Double rating;
    @JsonProperty("categories")
    private List<CategoryDto> categories;
}
