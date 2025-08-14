package patricio.santamaria.productssearch.mapper;

import patricio.santamaria.productssearch.dto.CategoryDto;
import patricio.santamaria.productssearch.entity.Category;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class CategoryMapper {

    private static final CategoryMapper INSTANCE = new CategoryMapper();

    private CategoryMapper() {}

    public static CategoryMapper getInstance() {
            return INSTANCE;
    }

    public CategoryDto toDto(Category category) {
        log.info("ToDto");
        if (category != null) {
            CategoryDto categoryDto = new CategoryDto();
            categoryDto.setId(category.getId());
            categoryDto.setName(category.getName());
            categoryDto.setDescription(category.getDescription());
            return categoryDto;
        }
        return null;
    }

    public Category toEntity(CategoryDto categoryDto) {
        log.info("ToEntity");
        if (categoryDto != null) {
            Category category = new Category();
            category.setId(categoryDto.getId());
            category.setName(categoryDto.getName());
            category.setDescription(categoryDto.getDescription());
            return category;
        }
        return null;
    }

    public List<CategoryDto> toDtos(List<Category> categories) {
        log.info("ToDtos");
        if (categories != null && !categories.isEmpty()) {
            return categories.stream().map(this::toDto).toList();
        }
        return List.of();
    }

    private List<Category> toEntities(List<CategoryDto> categoryDtos) {
        log.info("ToEntities");
        if (categoryDtos != null && !categoryDtos.isEmpty()) {
            return categoryDtos.stream().map(this::toEntity).toList();
        }
        return List.of();
    }


}
