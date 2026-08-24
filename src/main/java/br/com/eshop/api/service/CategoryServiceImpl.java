package br.com.eshop.api.service;

import br.com.eshop.api.exceptions.APIException;
import br.com.eshop.api.exceptions.ResourceNotFoundException;
import br.com.eshop.api.model.Category;
import br.com.eshop.api.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService{

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Category> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        if(categories.isEmpty()) {
            throw new APIException("nenhuma categoria cadastrada no momento");
        }

        return categories;
    }

    @Override
    public void createCategory(Category category) {
        Category existingCategory = categoryRepository.findByCategoryName(category.getCategoryName());
        if(existingCategory != null) {
            throw new APIException("categoria com o nome " + category.getCategoryName() + " já existe");
        }
        categoryRepository.save(category);
    }

    @Override
    public String deleteCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("categoria", "Id", categoryId));


        categoryRepository.delete(category);
        return "Categoria com o Id " + category.getCategoryId() + " foi deletada";
    }

    @Override
    public Category updateCategory(Category category, Long categoryId) {

        Category savedCategory = categoryRepository.findById(categoryId)
                .orElseThrow(() ->  new ResourceNotFoundException("Categoria", "Id", categoryId));


        category.setCategoryId(categoryId);
        savedCategory = categoryRepository.save(category);
        return savedCategory;

    }
}
