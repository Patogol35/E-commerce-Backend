package patricio.santamaria.productssearch.exception;

import patricio.santamaria.productssearch.dto.ResponseDto;
import patricio.santamaria.productssearch.dto.exception.CustomException;
import patricio.santamaria.productssearch.dto.exception.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class HandlerException {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ResponseDto> handlerException(Exception e) {
        ResponseDto responseDto = new ResponseDto();
        responseDto.setStatusCode(400);
        responseDto.setMessage(e.getMessage());
        return ResponseEntity.badRequest().body(responseDto);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ResponseDto> handlerNotFoundException(Exception e) {
        ResponseDto responseDto = new ResponseDto();
        responseDto.setStatusCode(404);
        responseDto.setMessage(e.getMessage());
        return ResponseEntity.status(404).body(responseDto);
    }


}
