package patricio.santamaria.productssearch.repository.product;


import patricio.santamaria.productssearch.entity.Product;
import patricio.santamaria.productssearch.repository.IBaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface IProductRepository extends IBaseRepository<Product, Long> {

    @Query("SELECT p FROM Product p JOIN p.categories c WHERE c.id = :categoryId and p.active = true")
    List<Product> findByCategoryId(@Param("categoryId") Long categoryId);

    @Query("SELECT p FROM Product p WHERE  p.rating > :rating and p.price < :price and p.active = true")
    List<Product> findByPriceMinorAndRating(@Param("price") Integer price, @Param("rating") Double rating);
    List<Product> findByPriceLessThanAndRatingGreaterThanAndActiveTrue(@Param("price") Integer price, @Param("rating") Double rating);

    List<Product> findByDiscountGreaterThanAndPriceLessThanAndActiveTrue(Integer discount, BigDecimal price);

    List<Product> findByNameLikeIgnoreCaseAndActiveTrue(String name);


}
