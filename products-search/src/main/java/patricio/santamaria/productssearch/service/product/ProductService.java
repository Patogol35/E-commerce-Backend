package patricio.santamaria.productssearch.service.product;

import patricio.santamaria.productssearch.dto.ProductDto;
import patricio.santamaria.productssearch.dto.ResponseDto;
import patricio.santamaria.productssearch.dto.ValidateStockDto;
import patricio.santamaria.productssearch.dto.exception.CustomException;
import patricio.santamaria.productssearch.dto.exception.NotFoundException;
import patricio.santamaria.productssearch.entity.Category;
import patricio.santamaria.productssearch.entity.Product;
import patricio.santamaria.productssearch.mapper.ProductMapper;
import patricio.santamaria.productssearch.repository.product.IProductRepository;
import patricio.santamaria.productssearch.service.category.ICategoryService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@Slf4j
public class ProductService implements IProductService {

    private final IProductRepository productRepository;
    private final ICategoryService categoryService;


    public ProductService(IProductRepository productRepository, ICategoryService categoryService) {
        this.productRepository = productRepository;
        this.categoryService = categoryService;
    }

    @Override
    @Transactional
    public ResponseDto deleteProduct(Long id) {
        log.info("deleteProduct - Id: {}", id);
        Product product = productRepository.findById(id).orElse(null);
        if (Objects.isNull(product)) {
            throw new CustomException("No se encontró el producto");
        }
        productRepository.delete(product);
        return new ResponseDto("Producto eliminado correctamente", 200, null);
    }
    @Override
    @Transactional
    public ResponseDto saveProduct(ProductDto productDto) {
        log.info("saveProduct - Product: {}", productDto);
        List<Category> categories = getCategoriesFromDto(productDto);
        if (categories.isEmpty() || categories.size() != productDto.getCategories().size()) {
            return new ResponseDto("No se encontraron categorias", 404, null);
        }
        Product product = ProductMapper.getInstance().toEntity(productDto);
        product.setCategories(categories);
        productRepository.save(product);
        return new ResponseDto("Producto guardado correctamente", 201, ProductMapper.getInstance().toDto(product));
    }

    @Override
    public Product findProductEntityById(Long id) {
        log.info("findProductById - Id: {}", id);
        Product product = productRepository.findById(id).orElse(null);
        if (Objects.isNull(product)) {
            throw new CustomException("No se encontro el producto");
        }
        return product;
    }

    @Override
    public ResponseDto findProductById(Long id) {
        log.info("findProductById - Id: {}", id);
        Product product = productRepository.findById(id).orElse(null);
        if (Objects.isNull(product)) {
            return new ResponseDto("No se encontro el producto", 404, null);
        }
        return new ResponseDto("Producto encontrado", 200, ProductMapper.getInstance().toDto(product));
    }

    @Override
    public List<ProductDto> findAllProducts() {
        log.info("findAllProducts");
        return ProductMapper.getInstance().toDtos(productRepository.findAll());
    }

    @Override
    public List<ProductDto> findProductsByName(String name) {
        log.info("findProductsById");
        List<Product> products = productRepository.findByNameLikeIgnoreCaseAndActiveTrue(name.concat("%"));
        if (products.isEmpty()) {
            throw new NotFoundException("No se encontraron productos");
        }
        return ProductMapper.getInstance().toDtos(products);
    }

    @Override
    public List<ProductDto> findProductsWithDiscountAndPriceMinor(BigDecimal price) {
        log.info("findProductsWithDiscountAndPriceMinor");
        List<Product> products = productRepository.findByDiscountGreaterThanAndPriceLessThanAndActiveTrue(0, price);
        if (products.isEmpty()) {
            throw new NotFoundException("No se encontraron productos para el precio indicado");
        }
        return ProductMapper.getInstance().toDtos(products);
    }

    @Override
    public List<ProductDto> findProductsPriceMinorAnRating(Integer price, Double rating) {
        log.info("findProductsPriceMinorAnRating");
        return ProductMapper.getInstance().toDtos(productRepository.findByPriceLessThanAndRatingGreaterThanAndActiveTrue(price, rating));
    }

    @Override
    public List<ProductDto> findProductByCategory(Long id) {
        log.info("findProductByCategory");
        Category category = categoryService.findById(id);
        if (Objects.isNull(category)) {
            throw new NotFoundException("No se encontro la categoria");
        }
        return ProductMapper.getInstance().toDtos(productRepository.findByCategoryId(id));
    }

    @Override
    @Transactional
    public ResponseDto updateProduct(ProductDto productDto) {
        log.info("updateProduct - Product: {}", productDto);
        Product productFound = productRepository.findById(productDto.getId()).orElse(null);
        if (productFound == null) {
            return new ResponseDto("No se encontro el producto", 400, null);
        }
        productFound.setName(Objects.requireNonNullElse(productDto.getName(), productFound.getName()));
        productFound.setSortDescription(Objects.requireNonNullElse(productDto.getSortDescription(), productFound.getSortDescription()));
        productFound.setLongDescription(Objects.requireNonNullElse(productDto.getLongDescription(), productFound.getLongDescription()));
        productFound.setPrice(Objects.requireNonNullElse(productDto.getPrice(), productFound.getPrice()));
        productFound.setActive(Objects.requireNonNullElse(productDto.getActive(), productFound.isActive()));
        productFound.setStock(Objects.requireNonNullElse(productDto.getStock(), productFound.getStock()));
        productRepository.save(productFound);
        return new ResponseDto("Producto actualizado correctamente", 200, ProductMapper.getInstance().toDto(productFound));
    }

    @Override
    public ResponseDto activeDeactiveProduct(ProductDto productDto) {
        Long id = productDto.getId();
        if (id != null) {
            Product productFound = productRepository.findById(id).orElse(null);
            if (productFound != null) {
                boolean activate = productFound.isActive();
                productFound.setActive(!activate);
                productRepository.save(productFound);
                return new ResponseDto("Producto modificado correctamente", 200, Map.of("active", !activate));
            }
            return new ResponseDto("No se encontro el producto", 404, null);
        }
        return new ResponseDto("No se encontro el producto", 400, null);
    }

    @Override
    public ResponseDto validateStock(List<ValidateStockDto> stockDtos) {
        log.info("validateStock - Stock: {}", stockDtos);
        List<Long> products = new ArrayList<>();
        stockDtos.forEach(vs -> {
            Product product = productRepository.findById(vs.getIdProduct()).orElse(null);
            if (product == null) throw new NotFoundException("No se encontro el producto: ".concat(vs.getIdProduct().toString()));
            if (product.getStock() < vs.getQuantity()) {
                products.add(vs.getIdProduct());
            }
        });
        if (products.isEmpty()) {
            return new ResponseDto("Stock disponible", 200, null);
        }
        return new ResponseDto("No hay stock suficiente para los productos: " + products, 400, null);
    }

    private List<Category> getCategoriesFromDto(ProductDto productDto) {
        List<Category> categories = new ArrayList<>();
        productDto.getCategories().forEach(categoryDto -> {
            Category category = categoryService.findById(categoryDto.getId());
            if (category != null) {
                categories.add(category);
            }
        });
        return categories;
    }



}
