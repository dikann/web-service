package com.dikann.webservice.service;

import com.dikann.webservice.dto.CategoryDto;
import com.dikann.webservice.entity.Category;
import com.dikann.webservice.exception.ObjectNotFoundException;
import com.dikann.webservice.repository.CategoryRepository;
import com.dikann.webservice.utils.CustomerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

import static com.dikann.webservice.utils.ApplicationConst.errorObjectNoFoundMessage;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CustomerMapper customerMapper;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository, CustomerMapper customerMapper) {
        this.categoryRepository = categoryRepository;
        this.customerMapper = customerMapper;
    }

    public Category addCategory(Category category) {
        category.setCreatedDate(LocalDateTime.now());
        return categoryRepository.save(category);
    }

    public Category getCategory(Long id) {
        Optional<Category> categoryOptional = categoryRepository.findById(id);
        if (categoryOptional.isEmpty())
            throw new ObjectNotFoundException(errorObjectNoFoundMessage);

        return categoryOptional.get();
    }

    public List<Category> getAllCategories(Integer pageNo, Integer pageSize, String sortBy) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<Category> categoryPage = categoryRepository.findAll(paging);

        if (categoryPage.hasContent())
            return categoryPage.getContent();

        return new ArrayList<Category>();
    }

    public Category updateCategory(Long id, CategoryDto categoryDto) {
        Optional<Category> categoryOptional = categoryRepository.findById(id);
        if (categoryOptional.isEmpty())
            throw new ObjectNotFoundException(errorObjectNoFoundMessage);

        Category category = categoryOptional.get();
        customerMapper.merge(categoryDto, category);
        category.setModifiedDate(LocalDateTime.now());
        return categoryRepository.save(category);
    }

    public Map<Object, Object> deleteCategory(Long id) {
        Optional<Category> categoryOptional = categoryRepository.findById(id);
        if (categoryOptional.isEmpty())
            throw new ObjectNotFoundException(errorObjectNoFoundMessage);

        categoryRepository.deleteById(id);
        Map<Object, Object> model = new HashMap<>();
        model.put("id", id);
        model.put("success", true);
        return model;
    }
}
