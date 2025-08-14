package patricio.santamaria.productssearch.service.product;

import patricio.santamaria.productssearch.dto.ProductDto;
import patricio.santamaria.productssearch.dto.ResponseDto;
import patricio.santamaria.productssearch.dto.ValidateStockDto;
import patricio.santamaria.productssearch.entity.Product;

import java.math.BigDecimal;
import java.util.List;

public interface IProductService {
    ResponseDto saveProduct(ProductDto productDto);
    Product findProductEntityById(Long id);
    ResponseDto findProductById(Long id);
    List<ProductDto> findAllProducts();
    List<ProductDto> findProductByCategory(Long id);
    ResponseDto updateProduct(ProductDto productDto);
    ResponseDto deleteProduct(Long id); 
    ResponseDto activeDeactiveProduct(ProductDto productDto);
    ResponseDto validateStock(List<ValidateStockDto> stockDtos);
    List<ProductDto> findProductsByName(String name);
    List<ProductDto> findProductsWithDiscountAndPriceMinor(BigDecimal price);
    List<ProductDto> findProductsPriceMinorAnRating(Integer price, Double rating);
}
