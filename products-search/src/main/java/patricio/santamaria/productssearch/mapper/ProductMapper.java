package patricio.santamaria.productssearch.mapper;

import patricio.santamaria.productssearch.dto.ProductDto;
import patricio.santamaria.productssearch.entity.Product;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class ProductMapper {

    private static final ProductMapper INSTANCE = new ProductMapper();

    private ProductMapper() {}

    public static ProductMapper getInstance() {
            return INSTANCE;
    }

    public ProductDto toDto(Product product) {
        log.info("ToDto");
        if (product != null) {
            ProductDto productDto = new ProductDto();
            productDto.setId(product.getId());
            productDto.setName(product.getName());
            productDto.setSortDescription(product.getSortDescription());
            productDto.setLongDescription(product.getLongDescription());
            productDto.setPrice(product.getPrice());
            productDto.setActive(product.isActive());
            productDto.setStock(product.getStock());
            productDto.setDiscount(product.getDiscount());
            productDto.setRating(product.getRating());
            productDto.setCategories(CategoryMapper.getInstance().toDtos(product.getCategories()));
            return productDto;
        }
        return null;
    }

    public Product toEntity(ProductDto productDto) {
        log.info("ToEntity");
        if (productDto != null) {
            Product product = new Product();
            product.setId(productDto.getId());
            product.setName(productDto.getName());
            product.setSortDescription(productDto.getSortDescription());
            product.setLongDescription(productDto.getLongDescription());
            product.setPrice(productDto.getPrice());
            product.setActive(productDto.getActive());
            product.setStock(productDto.getStock());
            product.setDiscount(productDto.getDiscount());
            product.setRating(productDto.getRating());
            return product;
        }
        return null;
    }

    public List<ProductDto> toDtos(List<Product> products) {
        log.info("ToDtos");
        if (products != null && !products.isEmpty()) {
            return products.stream().map(this::toDto).toList();
        }
        return List.of();
    }

    public List<Product> toEntities(List<ProductDto> productDtos) {
        log.info("ToEntities");
        if (productDtos != null && !productDtos.isEmpty()) {
            return productDtos.stream().map(this::toEntity).toList();
        }
        return List.of();
    }


}
