package patricio.santamaria.productssearch.controller;

import patricio.santamaria.productssearch.dto.ResponseDto;
import patricio.santamaria.productssearch.entity.Image;
import patricio.santamaria.productssearch.service.image.IImageService;
import patricio.santamaria.productssearch.util.ImageUtility;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/image")
@Slf4j
@RequiredArgsConstructor
public class ImageController {

    private final IImageService iImageService;
    
    @PostMapping()
    public ResponseEntity<ResponseDto> uploadImage(@RequestParam("file") MultipartFile file, @RequestParam("product_id") Long id) {
        long start = System.currentTimeMillis();
        // generate random file name
        ResponseDto responseDto = iImageService.saveImage(file, id);
        long end = System.currentTimeMillis();
        log.info("uploadImage - Time: {}", (end - start));
        if (responseDto.getStatusCode().equals(201)) {
            return ResponseEntity.ok(responseDto);
        }
        return ResponseEntity.badRequest().body(responseDto);
    }

    @GetMapping(path = {"/{name}"})
    public ResponseEntity<byte[]> getImage(@PathVariable("name") String name) {
        long start = System.currentTimeMillis();
        Image image = iImageService.getImage(name);
        long end = System.currentTimeMillis();
        log.info("getImage - Time: {}", (end - start));
        if (image == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity
                .ok()
                .contentType(MediaType.valueOf(image.getType()))
                .body(ImageUtility.decompressImage(image.getImage()));
    }

}
