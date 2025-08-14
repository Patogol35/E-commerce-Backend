package patricio.santamaria.productsoperator.exception;

import patricio.santamaria.productsoperator.dto.ResponseDto;
import patricio.santamaria.productsoperator.dto.exception.CustomException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

@RestControllerAdvice
public class HandlerException {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ResponseDto> handlerException(Exception e) {
        ResponseDto responseDto = new ResponseDto();
        responseDto.setStatusCode(500);
        responseDto.setMessage(e.getMessage());
        return ResponseEntity.internalServerError().body(responseDto);
    }

    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<ResponseDto> handlerHttpClientErrorException(HttpClientErrorException e) {
        ResponseDto responseDto = new ResponseDto();
        responseDto.setStatusCode(e.getRawStatusCode());
        responseDto.setMessage(e.getMessage());
        return ResponseEntity.status(e.getRawStatusCode()).body(responseDto);
    }

}
