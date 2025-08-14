package patricio.santamaria.productsoperator.http;


import patricio.santamaria.productsoperator.dto.ProductDto;
import patricio.santamaria.productsoperator.dto.ResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@Slf4j
public class RestClient {

    @Value("${microservicio.products.validate-stock}")
    String urlValidateStock;

    @Value("${microservicio.products.find-by-id}")
    String urlFindById;

    private final RestTemplate restTemplate;

    public RestClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ResponseDto sendRequestStock(String jsonRequest) {
        log.info("Validando stock al microservicio de productos");
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        HttpEntity<String> request = new HttpEntity<>(jsonRequest, headers);
        ResponseEntity<ResponseDto> response = restTemplate.postForEntity(urlValidateStock, request, ResponseDto.class);
        return response.getBody();
    }

    public ResponseDto sendRequestFindProductById(Long id) {
        log.info("Buscando producto por id al microservicio de productos");
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        HttpEntity<String> request = new HttpEntity<>(headers);
        return restTemplate.getForObject(urlFindById.concat(""+id), ResponseDto.class);
    }

}
