package patricio.santamaria.productssearch.repository.image;

import patricio.santamaria.productssearch.entity.Image;
import patricio.santamaria.productssearch.repository.IBaseRepository;

public interface IImageRepository extends IBaseRepository<Image, Long> {
    public Image findByName(String name);
}
