package com.dikann.webservice.service;

import com.dikann.webservice.dto.CategoryDto;
import com.dikann.webservice.entity.Category;
import com.dikann.webservice.enums.SortByEnum;
import com.dikann.webservice.enums.SortDirEnum;
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

import static com.dikann.webservice.utils.ApplicationConst.errorObjectNotFoundMessage;

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
            throw new ObjectNotFoundException(errorObjectNotFoundMessage);

        return categoryOptional.get();
    }

    public List<Category> getAllCategories(Integer pageNo, Integer pageSize, SortByEnum sortBy, SortDirEnum sortDir) {
        Pageable paging = PageRequest.of(pageNo, pageSize,
                sortDir == SortDirEnum.ASC ? Sort.by(sortBy.toString()).ascending()
                        : Sort.by(sortBy.toString()).descending());
        Page<Category> categoryPage = categoryRepository.findAll(paging);

        if (categoryPage.hasContent())
            return categoryPage.getContent();

        return new ArrayList<Category>();
    }

    public Category updateCategory(Long id, CategoryDto categoryDto) {
        Optional<Category> categoryOptional = categoryRepository.findById(id);
        if (categoryOptional.isEmpty())
            throw new ObjectNotFoundException(errorObjectNotFoundMessage);

        Category category = categoryOptional.get();
        customerMapper.merge(categoryDto, category);
        category.setModifiedDate(LocalDateTime.now());
        return categoryRepository.save(category);
    }

    public Map<Object, Object> deleteCategory(Long id) {
        Optional<Category> categoryOptional = categoryRepository.findById(id);
        if (categoryOptional.isEmpty())
            throw new ObjectNotFoundException(errorObjectNotFoundMessage);

        categoryRepository.deleteById(id);
        Map<Object, Object> model = new HashMap<>();
        model.put("id", id);
        model.put("success", true);
        return model;
    }
}
