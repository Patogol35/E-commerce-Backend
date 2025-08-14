package patricio.santamaria.productssearch.service.image;

import patricio.santamaria.productssearch.dto.ResponseDto;
import patricio.santamaria.productssearch.dto.exception.CustomException;
import patricio.santamaria.productssearch.entity.Image;
import patricio.santamaria.productssearch.entity.Product;
import patricio.santamaria.productssearch.repository.image.IImageRepository;
import patricio.santamaria.productssearch.service.product.IProductService;
import patricio.santamaria.productssearch.util.ImageUtility;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class ImageService implements IImageService {

    private final IImageRepository imageRepository;
    private final IProductService productService;

    @Override
    public ResponseDto saveImage(MultipartFile file, Long productId) {
        log.info("saveImage - Image: {}", file);
        if (file == null || file.isEmpty()) return new ResponseDto("No se encontro la imagen", 400, null);
        Product product = productService.findProductEntityById(productId);
        if (product == null) return new ResponseDto("No se encontro el producto", 400, null);
        Image imageEntity = new Image();
        try {
            String type = file.getContentType();
            if (type == null) {
                return new ResponseDto("No se encontro el tipo de imagen", 400, null);
            }
            String extension = type.substring(type.indexOf("/") + 1);
            String name = generateUniqueIdentifier() + "." + extension;
            imageEntity.setImage(ImageUtility.compressImage(file.getBytes()));
            imageEntity.setName(name);
            imageEntity.setType(type);
            imageEntity.setProduct(product);
            imageRepository.save(imageEntity);
        } catch (IOException e) {
            throw new CustomException(e.getMessage());
        }
        return new ResponseDto("Imagen guardada correctamente", 201, null);
    }

    @Override
    public Image getImage(String name) {
        return imageRepository.findByName(name);
    }

    private static String generateUniqueIdentifier() {
        Date timestamp = new Date();
        String timestampString = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(timestamp);
        String uniqueId = UUID.randomUUID().toString().replace("-", "");
        return timestampString + uniqueId;
    }

}
