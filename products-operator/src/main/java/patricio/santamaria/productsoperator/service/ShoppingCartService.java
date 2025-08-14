package patricio.santamaria.productsoperator.service;


import com.fasterxml.jackson.core.JsonProcessingException;

import com.fasterxml.jackson.databind.ObjectMapper;
import patricio.santamaria.productsoperator.dto.*;
import patricio.santamaria.productsoperator.dto.exception.CustomException;
import patricio.santamaria.productsoperator.entity.Item;
import patricio.santamaria.productsoperator.entity.ShoppingCart;
import patricio.santamaria.productsoperator.http.RestClient;
import patricio.santamaria.productsoperator.repository.IShoppingCartRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@Slf4j
public class ShoppingCartService {

    private final RestClient restClient;
    private final IShoppingCartRepository shoppingCartRepository;
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public ShoppingCartService(IShoppingCartRepository shoppingCartRepository, RestClient restClient) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.restClient = restClient;
    }

    public ResponseDto removeItem(Long removeItem) {
        log.info("removeItem - Item: {}", removeItem);
        ShoppingCart shoppingCart = findByStatus(); // Busca el carrito de compras con estado 0 (abierto)
        if (Objects.isNull(shoppingCart)) {
            return new ResponseDto("No se encontro el carrito de compras", 400, null);
        }
        if (shoppingCart.getStatus() == 1) {
            return new ResponseDto("El carrito de compras ya se encuentra cerrado", 400, null);
        }
        List<Item> items = shoppingCart.getItems();
        int sizeBefore = items.size();
        items.removeIf(item -> item.getIdProduct().equals(removeItem));
        if (sizeBefore == items.size()) {
            return new ResponseDto("No se encontro el item", 404, null);
        }
        shoppingCart.setItems(items);
        shoppingCartRepository.save(shoppingCart);
        return new ResponseDto("Item eliminado correctamente", 200, null);
    }

    public List<Item> getAllItemsFromShoppingCart() {
        List<Item> items = new ArrayList<>();
        ShoppingCart shoppingCart = findByStatus(); // Busca el carrito de compras con estado 0 (abierto)
        if (Objects.nonNull(shoppingCart)) {
            items = shoppingCart.getItems();
        }
        return items;

    }

    public ResponseDto payment(ShoppingDto shoppingDto) {
        log.info("finishShoppingCart - Shopping: {}", shoppingDto);
        ShoppingCart shoppingCart = findByStatus(); // Busca el carrito de compras con estado 0 (abierto)
        if (Objects.isNull(shoppingCart)) {
            return new ResponseDto("No se encontro el carrito de compras", 400, null);
        }
        if (shoppingCart.getStatus() == 1) {
            return new ResponseDto("El carrito de compras ya se encuentra cerrado", 400, null);
        }
        shoppingCart.setStatus(1);
        shoppingCart.setStatusDescription("SHOPPING_CART_PAID");
        shoppingCartRepository.save(shoppingCart);
        return new ResponseDto("Compra realizada correctamente", 200, null);
    }

    public ResponseDto createShoppingCart(ShoppingDto shoppingDto) {
        log.info("createShoppingCart - Shopping: {}", shoppingDto);
        ShoppingCart shoppingCartFound = findByStatus(); // Busca el carrito de compras con estado 0 (abierto)
        if (shoppingCartFound == null) {
            log.info("Creating Shopping cart");
            shoppingCartFound = new ShoppingCart();
            shoppingCartFound.setClient(Objects.requireNonNullElse(shoppingDto.getClient(), "Unknown"));
            shoppingCartFound.setDescription(Objects.requireNonNullElse(shoppingDto.getDescription(), "Carrito de compras"));
            return createShoppingCart(shoppingDto, shoppingCartFound);
        }
        return addItemToShoppingCart(shoppingDto, shoppingCartFound);
    }

    private ResponseDto addItemToShoppingCart(ShoppingDto shoppingDto, ShoppingCart shoppingCartFound) {
        log.info("Add item to Shopping cart");
        List<Item> items = shoppingCartFound.getItems();
        List<ValidateStockDto> validateStockDtos = new ArrayList<>();
        shoppingDto.getItems().forEach((item -> {
            validateStockDtos.add(new ValidateStockDto(item.getIdProduct(), item.getQuantity()));
        }));
        if (validateStock(validateStockDtos)) {
            boolean contains = items.stream().anyMatch(item -> item.getIdProduct().equals(shoppingDto.getItems().get(0).getIdProduct()));
            if (contains) {
                items.forEach(item -> {
                    ItemDto itemUpdate = shoppingDto.getItems().get(0);
                    if (item.getIdProduct().equals(itemUpdate.getIdProduct())) {
                        ProductDto product = getProduct(itemUpdate.getIdProduct());
                        item.setProduct(product.getName());
                        item.setQuantity(itemUpdate.getQuantity());
                        item.setSubtotal(BigDecimal.valueOf(item.getQuantity()).multiply(product.getPrice()));
                        item.setIdProduct(product.getId());
                    }
                });
            } else {
                ItemDto add = shoppingDto.getItems().get(0);
                ProductDto product = getProduct(add.getIdProduct());
                Item itemNew = new Item();
                itemNew.setProduct(product.getName());
                itemNew.setQuantity(add.getQuantity());
                itemNew.setSubtotal(BigDecimal.valueOf(add.getQuantity()).multiply(product.getPrice()));
                itemNew.setIdProduct(product.getId());
                items.add(itemNew);
            }
            BigDecimal total = items.stream().map(Item::getSubtotal).reduce(BigDecimal.ZERO, BigDecimal::add);
            shoppingCartFound.setTotal(total);
            shoppingCartRepository.save(shoppingCartFound);
            return new ResponseDto("El carrito fue actualizado", 200, shoppingCartFound);
        }
        return new ResponseDto("No hay Stock suficiente", 400, null);
    }

    private ProductDto getProduct(Long id) {
        ResponseDto responseDto = restClient.sendRequestFindProductById(id);
        log.info("ResponseDto Price: {}", responseDto);
        if (responseDto.getStatusCode().equals(200)) {
            @SuppressWarnings("unchecked")
            Map<String, Object> data = (Map<String, Object>) responseDto.getData();
            ProductDto productDto = new ProductDto();
            productDto.setId(Long.parseLong(data.get("id").toString()));
            productDto.setName(data.get("name").toString());
            productDto.setPrice(BigDecimal.valueOf(Double.parseDouble(data.get("price").toString())));
            return productDto;
        }
        throw new CustomException("Error al obtener el precio del producto");
    }

    private ResponseDto createShoppingCart(ShoppingDto shoppingDto, ShoppingCart shoppingCartFound) {
        List<ItemDto> itemDtos = shoppingDto.getItems();
        if (itemDtos != null && !itemDtos.isEmpty()) {
            List<ValidateStockDto> validateStockDtos = new ArrayList<>();
            itemDtos.forEach((item -> validateStockDtos.add(new ValidateStockDto(item.getIdProduct(), item.getQuantity()))));
            if (validateStock(validateStockDtos)) {
                List<Item> items = getItemsFromDto(itemDtos);
                shoppingCartFound.setItems(items);
                BigDecimal total = items.stream().map(Item::getSubtotal).reduce(BigDecimal.ZERO, BigDecimal::add);
                shoppingCartFound.setTotal(total);
            } else {
                return new ResponseDto("No hay Stock suficiente", 400, null);
            }
            shoppingCartRepository.save(shoppingCartFound);
            return new ResponseDto("Carrito de compras creado correctamente", 201, shoppingCartFound.getId());
        }
        return new ResponseDto("No se puede crear un carrito de compras sin items", 400, null);
    }

    private List<Item> getItemsFromDto(List<ItemDto> itemDtos) {
        List<Item> items = new ArrayList<>();
        for (ItemDto itemDto : itemDtos) {
            Item item = new Item();
            ProductDto product = getProduct(itemDto.getIdProduct());
            item.setIdProduct(itemDto.getIdProduct());
            item.setQuantity(itemDto.getQuantity());
            item.setSubtotal(BigDecimal.valueOf(itemDto.getQuantity()).multiply(product.getPrice()));
            item.setIdProduct(product.getId());
            item.setProduct(product.getName());
            items.add(item);
        }
        return items;
    }

    private boolean validateStock(List<ValidateStockDto> validateStockDtos) {
        try {
            String json = objectMapper.writeValueAsString(validateStockDtos);
            log.info("Request: {}", json);
            ResponseDto response = restClient.sendRequestStock(json);
            log.info("Response: {}", response);
            if (response.getStatusCode().equals(200)) {
                return true;
            }
            return false;
        } catch (JsonProcessingException e) {
            log.error("Error al convertir el objeto a JSON: {}", e.getMessage());
            throw new CustomException("Error al convertir el objeto a JSON");
        }
    }

    private ShoppingCart findByStatus() {
        return shoppingCartRepository.findByStatus(0).orElse(null);
    }


}
