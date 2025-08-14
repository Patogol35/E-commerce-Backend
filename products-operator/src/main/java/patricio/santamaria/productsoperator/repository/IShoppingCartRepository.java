package patricio.santamaria.productsoperator.repository;

import patricio.santamaria.productsoperator.entity.ShoppingCart;

import java.util.Optional;

public interface IShoppingCartRepository extends IRepositoryBase<ShoppingCart, Long> {

    public Optional<ShoppingCart> findByStatus(Integer status);

}
