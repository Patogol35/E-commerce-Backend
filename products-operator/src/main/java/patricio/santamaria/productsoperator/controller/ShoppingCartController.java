package patricio.santamaria.productsoperator.controller;

import patricio.santamaria.productsoperator.dto.ResponseDto;
import patricio.santamaria.productsoperator.dto.ShoppingDto;
import patricio.santamaria.productsoperator.entity.Item;
import patricio.santamaria.productsoperator.service.ShoppingCartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/shopping-cart")
@Slf4j
public class ShoppingCartController {

    private final ShoppingCartService shoppingCartService;

    public ShoppingCartController(ShoppingCartService shoppingCartService) {
        this.shoppingCartService = shoppingCartService;
    }

    @PostMapping(produces = "application/json", consumes = "application/json")
    public ResponseEntity<ResponseDto> saveShoppingCart(@RequestBody ShoppingDto shoppingDto) {
        long start = System.currentTimeMillis();
        ResponseDto responseDto = shoppingCartService.createShoppingCart(shoppingDto);
        long end = System.currentTimeMillis();
        log.info("saveShoppingCart - Time: {}", (end - start));
        if (responseDto.getStatusCode().equals(201)) {
            return ResponseEntity.status(201).body(responseDto);
        }
        return ResponseEntity.badRequest().body(responseDto);
    }

    @GetMapping(value = "/items", produces = "application/json")
    public ResponseEntity<List<Item>> getAllItemsFromShoppingCart() {
        long start = System.currentTimeMillis();
        List<Item> items = shoppingCartService.getAllItemsFromShoppingCart();
        long end = System.currentTimeMillis();
        log.info("findAllItems - Time: {}", (end - start));
        return ResponseEntity.ok(items);
    }

    @DeleteMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<ResponseDto> removeItem(@PathVariable Long id) {
        long start = System.currentTimeMillis();
        ResponseDto responseDto = shoppingCartService.removeItem(id);
        long end = System.currentTimeMillis();
        log.info("deleteItemFromShoppingCart - Time: {}", (end - start));
        if (responseDto.getStatusCode().equals(200)) {
            return ResponseEntity.ok(responseDto);
        } else if (responseDto.getStatusCode().equals(404)) {
            return ResponseEntity.status(404).body(responseDto);
        }
        return ResponseEntity.badRequest().body(responseDto);
    }

}
