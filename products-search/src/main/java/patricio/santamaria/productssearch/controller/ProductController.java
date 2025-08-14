package patricio.santamaria.productssearch.controller;

import patricio.santamaria.productssearch.dto.ProductDto;
import patricio.santamaria.productssearch.dto.ResponseDto;
import patricio.santamaria.productssearch.dto.ValidateStockDto;
import patricio.santamaria.productssearch.service.product.IProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/products")
@Slf4j
@RequiredArgsConstructor
public class ProductController {

    @Value("${regex.only.number}")
    String regexOnlyNumber;

    private final IProductService productService;

    @PostMapping(produces = "application/json", consumes = "application/json")
    public ResponseEntity<ResponseDto> saveProduct(@RequestBody ProductDto productDto) {
        long start = System.currentTimeMillis();
        ResponseDto responseDto = productService.saveProduct(productDto);
        long end = System.currentTimeMillis();
        log.info("saveProduct - Time: {}", (end - start));
        if (responseDto.getStatusCode().equals(201)) {
            return ResponseEntity.status(201).body(responseDto);
        } else if (responseDto.getStatusCode().equals(404)) {
            return ResponseEntity.status(404).body(responseDto);
        }
        return ResponseEntity.badRequest().body(responseDto);
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<ResponseDto> findProductById(@PathVariable Long id) {
        long start = System.currentTimeMillis();
        ResponseDto productDto = productService.findProductById(id);
        long end = System.currentTimeMillis();
        if (productDto.getStatusCode().equals(200)) {
            log.info("findProductById - Time: {}", (end - start));
            return ResponseEntity.ok(productDto);
        }
        return ResponseEntity.status(404).body(productDto);
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<ProductDto>> findAllProducts() {
        long start = System.currentTimeMillis();
        List<ProductDto> productDtos = productService.findAllProducts();
        long end = System.currentTimeMillis();
        log.info("findAllProducts - Time: {}", (end - start));
        return ResponseEntity.ok(productDtos);
    }

    @GetMapping(value = "/category/{id}", produces = "application/json")
    public ResponseEntity<List<ProductDto>> findProductByCategory(@PathVariable Long id) {
        long start = System.currentTimeMillis();
        List<ProductDto> productDtos = productService.findProductByCategory(id);
        long end = System.currentTimeMillis();
        log.info("findProductByCategory - Time: {}", (end - start));
        return ResponseEntity.ok(productDtos);
    }

    @PutMapping(produces = "application/json", consumes = "application/json")
    public ResponseEntity<ResponseDto> updateProduct(@RequestBody ProductDto productDto) {
        long start = System.currentTimeMillis();
        ResponseDto responseDto = productService.updateProduct(productDto);
        long end = System.currentTimeMillis();
        log.info("updateProduct - Time: {}", (end - start));
        if (responseDto.getStatusCode().equals(200)) {
            return ResponseEntity.ok(responseDto);
        }
        return ResponseEntity.badRequest().body(responseDto);
    }

    @PutMapping(value = "/active-deactivate", produces = "application/json", consumes = "application/json")
    public ResponseEntity<ResponseDto> activeDeactiveProduct(@RequestBody ProductDto productDto) {
        long start = System.currentTimeMillis();
        ResponseDto responseDto = productService.activeDeactiveProduct(productDto);
        long end = System.currentTimeMillis();
        log.info("activeDeactiveProduct - Time: {}", (end - start));
        if (responseDto.getStatusCode().equals(200)) {
            return ResponseEntity.ok(responseDto);
        }
        return ResponseEntity.badRequest().body(responseDto);
    }

    @GetMapping(value = "/name/{name}", produces = "application/json")
    public ResponseEntity<List<ProductDto>> findProductsByName(@PathVariable("name") String name) {
        long start = System.currentTimeMillis();
        List<ProductDto> productDtos = productService.findProductsByName(name);
        long end = System.currentTimeMillis();
        log.info("findProductsByName - Time: {}", (end - start));
        return ResponseEntity.ok(productDtos);
    }

    @GetMapping(value = "/price/{price}", produces = "application/json")
    public ResponseEntity<?> findProductDiscountAndPriceMinor(@PathVariable("price") String price) {
        long start = System.currentTimeMillis();
        if (price == null || price.isEmpty()) {
            price = "10";
        }
        if (!price.matches(regexOnlyNumber)) {
            return ResponseEntity.badRequest().body(new ResponseDto("Price is not a number", 400, null));
        }

        List<ProductDto> productDtos = productService.findProductsWithDiscountAndPriceMinor(BigDecimal.valueOf(Double.parseDouble(price)));
        if (productDtos.isEmpty()) {
            return ResponseEntity.badRequest().body(new ResponseDto("No products found", 404, null));
        }
        long end = System.currentTimeMillis();
        log.info("findProductDiscountAndPriceMinor - Time: {}", (end - start));
        return ResponseEntity.ok(productDtos);
    }

    @GetMapping(value = "/price/{price}/rating/{rating}", produces = "application/json")
    public ResponseEntity<Object> findProductByPriceMinorAndRating(@PathVariable("price") String price, @PathVariable("rating") String rating) {
        long start = System.currentTimeMillis();
        if (!price.matches(regexOnlyNumber)) {
            return ResponseEntity.badRequest().body(new ResponseDto("Price is not a number", 400, null));
        }
        if (!rating.matches(regexOnlyNumber)) {
            return ResponseEntity.badRequest().body(new ResponseDto("Rating is not a number", 400, null));
        }
        List<ProductDto> productDtos = productService.findProductsPriceMinorAnRating(Objects.requireNonNullElse(Integer.valueOf(price), 3), Objects.requireNonNullElse(Double.valueOf(rating), 4.0));
        long end = System.currentTimeMillis();
        log.info("findProductByPriceMinorAndRating - Time: {}", (end - start));
        return ResponseEntity.ok(productDtos);


    }

    @PostMapping(value = "/stock", produces = "application/json", consumes = "application/json")
    public ResponseEntity<ResponseDto> validateStock(@RequestBody List<ValidateStockDto> stockDtos) {
        long start = System.currentTimeMillis();
        ResponseDto responseDto = productService.validateStock(stockDtos);
        long end = System.currentTimeMillis();
        log.info("validateStock - Time: {}", (end - start));
        if (responseDto.getStatusCode().equals(200)) {
            return ResponseEntity.ok(responseDto);
        }
        return ResponseEntity.badRequest().body(responseDto);
    }

    @DeleteMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<ResponseDto> deleteProduct(@PathVariable Long id) {
        ResponseDto responseDto = productService.deleteProduct(id);
        if (responseDto.getStatusCode().equals(200)) {
            return ResponseEntity.ok(responseDto);
        }
        return ResponseEntity.badRequest().body(responseDto);
    }
}
