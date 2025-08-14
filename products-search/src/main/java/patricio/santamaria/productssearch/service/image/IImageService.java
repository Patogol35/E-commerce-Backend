package patricio.santamaria.productssearch.service.image;

import patricio.santamaria.productssearch.dto.ResponseDto;
import patricio.santamaria.productssearch.entity.Image;
import org.springframework.web.multipart.MultipartFile;

public interface IImageService {
    ResponseDto saveImage(MultipartFile file, Long productId);
    Image getImage(String name);
}
