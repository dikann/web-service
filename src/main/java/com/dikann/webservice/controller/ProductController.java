package com.dikann.webservice.controller;


import com.dikann.webservice.dto.ProductDto;
import com.dikann.webservice.entity.Product;
import com.dikann.webservice.enums.SortByProductEnum;
import com.dikann.webservice.enums.SortDirEnum;
import com.dikann.webservice.service.ProductService;
import com.dikann.webservice.utils.ApplicationConst;
import com.dikann.webservice.views.Views;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.ApiParam;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(ApplicationConst.baseUrl + "product")
@JsonView(value = {Views.Detailed.class})
public class ProductController {

    private final ProductService productService;
    private final ModelMapper mapper;

    @Autowired
    public ProductController(ProductService productService, ModelMapper mapper) {
        this.productService = productService;
        this.mapper = mapper;

        this.mapper.typeMap(Product.class, ProductDto.class).addMapping(Product::getCategory, ProductDto::setCategoryDto);
        this.mapper.typeMap(Product.class, ProductDto.class).addMapping(Product::getDiscount, ProductDto::setDiscountDto);
    }

    @PostMapping
    public ResponseEntity<ProductDto> addProduct(@RequestBody @Valid ProductDto productDto) {
        ProductDto productDtoResponse = mapper.map(productService.addProduct(productDto), ProductDto.class);
        return ResponseEntity.ok(productDtoResponse);
    }

    @GetMapping("{id}")
    public ResponseEntity<ProductDto> getProduct(@PathVariable("id") Long id) {
        ProductDto productDtoResponse = mapper.map(productService.getProduct(id), ProductDto.class);
        return ResponseEntity.ok(productDtoResponse);
    }

    @JsonView(value = {Views.Summery.class})
    @GetMapping
    public List<ProductDto> getAllProducts(@RequestParam(defaultValue = ApplicationConst.pageNo, name = "page") Integer pageNo,
                                           @RequestParam(defaultValue = ApplicationConst.pageSize, name = "page_size") Integer pageSize,
                                           @ApiParam(type = "String", allowableValues = "id, name, createdDate, modifiedDate, sku, price, quantity")
                                           @RequestParam(defaultValue = ApplicationConst.sortBy, name = "sort_by") SortByProductEnum sortBy,
                                           @ApiParam(type = "String", allowableValues = "asc, desc")
                                           @RequestParam(defaultValue = ApplicationConst.sortDir, name = "sort_dir") SortDirEnum sortDir) {

        return productService.getAllProducts(pageNo, pageSize, sortBy, sortDir).stream().map(product -> mapper.map(product, ProductDto.class))
                .collect(Collectors.toList());
    }

    @PutMapping("{id}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable("id") Long id, @RequestBody ProductDto productDto) {
        ProductDto productDtoResponse = mapper.map(productService.updateProduct(id, productDto), ProductDto.class);
        return ResponseEntity.ok(productDtoResponse);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> deleteProduct(@PathVariable("id") Long id) {
        return new ResponseEntity<Object>(productService.deleteProduct(id), HttpStatus.OK);
    }
}
