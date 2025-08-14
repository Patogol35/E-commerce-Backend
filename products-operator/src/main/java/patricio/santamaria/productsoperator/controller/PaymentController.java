package patricio.santamaria.productsoperator.controller;

import patricio.santamaria.productsoperator.dto.ResponseDto;
import patricio.santamaria.productsoperator.dto.ShoppingDto;
import patricio.santamaria.productsoperator.service.ShoppingCartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payments")
@Slf4j
public class PaymentController {

    private final ShoppingCartService shoppingCartService;

    public PaymentController(ShoppingCartService shoppingCartService) {
        this.shoppingCartService = shoppingCartService;
    }

    @PutMapping(produces = "application/json", consumes = "application/json")
    public ResponseEntity<ResponseDto> payment(@RequestBody ShoppingDto shoppingDto) {
        long start = System.currentTimeMillis();
        ResponseDto responseDto = shoppingCartService.payment(shoppingDto);
        long end = System.currentTimeMillis();
        log.info("payment - Time: {}", (end - start));
        if (responseDto.getStatusCode().equals(200)) {
            return ResponseEntity.ok(responseDto);
        }
        return ResponseEntity.badRequest().body(responseDto);
    }

}
