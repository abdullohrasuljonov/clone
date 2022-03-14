package uz.pdp.codingbat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.codingbat.entity.Category;
import uz.pdp.codingbat.payload.ApiResponse;
import uz.pdp.codingbat.payload.CategoryDto;
import uz.pdp.codingbat.repository.CategoryRepository;
import uz.pdp.codingbat.repository.LanguageRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    LanguageRepository languageRepository;

    public List<Category> all(){
        return categoryRepository.findAll();
    }

    public Category getById(Integer id){
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        return optionalCategory.orElse(null);
    }

    public ApiResponse addCategory(CategoryDto categoryDto){
        boolean exists = categoryRepository.existsByName(categoryDto.getName());
        if (exists)
            return new ApiResponse("Category name already exist!",false);
        Category category=new Category();
        category.setDescription(categoryDto.getDescription());
        category.setName(categoryDto.getName());
        category.setLanguages(categoryDto.getLanguages());
        categoryRepository.save(category);
        return new ApiResponse("Successfully added!",true);
    }

    public ApiResponse editCategory(Integer id,CategoryDto categoryDto){
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (!optionalCategory.isPresent())
            return new ApiResponse("Category not found!",false);
        boolean exists = categoryRepository.existsByName(categoryDto.getName());
        if (exists)
            return new ApiResponse("Category name already exist!",false);
        Category category=optionalCategory.get();
        category.setDescription(categoryDto.getDescription());
        category.setName(categoryDto.getName());
        category.setLanguages(categoryDto.getLanguages());
        categoryRepository.save(category);
        return new ApiResponse("Successfully edited!",true);
    }

    public ApiResponse deleteCategory(Integer id){
        try {
            categoryRepository.deleteById(id);
            return new ApiResponse("Successfully deleted!",true);
        }catch (Exception e){
            return new ApiResponse("Deleting error!",false);
        }
    }
}
