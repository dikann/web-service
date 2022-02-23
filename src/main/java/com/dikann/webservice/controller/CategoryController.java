package com.dikann.webservice.controller;

import com.dikann.webservice.dto.CategoryDto;
import com.dikann.webservice.entity.Category;
import com.dikann.webservice.enums.SortByEnum;
import com.dikann.webservice.enums.SortDirEnum;
import com.dikann.webservice.service.CategoryService;
import com.dikann.webservice.utils.ApplicationConst;
import com.dikann.webservice.views.Views;
import com.fasterxml.jackson.annotation.JsonView;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(ApplicationConst.baseUrl + "category")
@JsonView(value = {Views.Detailed.class})
public class CategoryController {

    private final CategoryService categoryService;
    private final ModelMapper mapper;

    @Autowired
    public CategoryController(CategoryService categoryService, ModelMapper mapper) {
        this.categoryService = categoryService;
        this.mapper = mapper;
    }


    @PostMapping
    public ResponseEntity<CategoryDto> addCategory(@RequestBody @Valid CategoryDto categoryDto) {
        Category category = mapper.map(categoryDto, Category.class);
        CategoryDto categoryDtoResponse = mapper.map(categoryService.addCategory(category), CategoryDto.class);
        return ResponseEntity.ok(categoryDtoResponse);
    }

    @GetMapping("{id}")
    public ResponseEntity<CategoryDto> getCategory(@PathVariable("id") Long id) {
        CategoryDto categoryDto = mapper.map(categoryService.getCategory(id), CategoryDto.class);
        return ResponseEntity.ok(categoryDto);
    }

    @GetMapping
    public List<CategoryDto> getAllCategories(@RequestParam(defaultValue = ApplicationConst.pageNo, name = "page") Integer pageNo,
                                              @RequestParam(defaultValue = ApplicationConst.pageSize, name = "page_size") Integer pageSize,
                                              @RequestParam(defaultValue = ApplicationConst.sortBy, name = "sort_by") SortByEnum sortBy,
                                              @RequestParam(defaultValue = ApplicationConst.sortDir, name = "sort_dir") SortDirEnum sortDir) {

        return categoryService.getAllCategories(pageNo, pageSize, sortBy, sortDir).stream().map(category -> mapper.map(category, CategoryDto.class))
                .collect(Collectors.toList());
    }

    @PutMapping("{id}")
    public ResponseEntity<CategoryDto> updateCategory(@PathVariable("id") Long id, @RequestBody CategoryDto categoryDto) {
        CategoryDto categoryDtoResponse = mapper.map(categoryService.updateCategory(id, categoryDto), CategoryDto.class);
        return ResponseEntity.ok(categoryDtoResponse);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> deleteCategory(@PathVariable("id") Long id) {
        return new ResponseEntity<Object>(categoryService.deleteCategory(id), HttpStatus.OK);
    }

}
