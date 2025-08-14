package patricio.santamaria.productssearch.service.category;

import patricio.santamaria.productssearch.entity.Category;
import patricio.santamaria.productssearch.repository.category.ICategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CategoryService implements ICategoryService {

    private final ICategoryRepository categoryRepository;

    public CategoryService(ICategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category findById(Long id) {
        log.info("findById - Category: {}", id);
        return categoryRepository.findById(id).orElse(null);
    }


}
