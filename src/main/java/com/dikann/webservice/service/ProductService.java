package com.dikann.webservice.service;


import com.dikann.webservice.dto.ProductDto;
import com.dikann.webservice.entity.Category;
import com.dikann.webservice.entity.Discount;
import com.dikann.webservice.entity.Product;
import com.dikann.webservice.enums.SortByEnum;
import com.dikann.webservice.enums.SortByProductEnum;
import com.dikann.webservice.enums.SortDirEnum;
import com.dikann.webservice.exception.ObjectNotFoundException;
import com.dikann.webservice.repository.CategoryRepository;
import com.dikann.webservice.repository.DiscountRepository;
import com.dikann.webservice.repository.ProductRepository;
import com.dikann.webservice.utils.CustomerMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

import static com.dikann.webservice.utils.ApplicationConst.errorObjectNotFoundMessage;
import static com.dikann.webservice.utils.ApplicationConst.errorObjectWithNameNotFoundMessage;


@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final DiscountRepository discountRepository;
    private final ModelMapper mapper;
    private final CustomerMapper customerMapper;

    @Autowired
    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository, DiscountRepository discountRepository, ModelMapper mapper, CustomerMapper customerMapper) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.discountRepository = discountRepository;
        this.mapper = mapper;
        this.customerMapper = customerMapper;
    }

    public Product addProduct(ProductDto productDto) {
        Optional<Category> category = categoryRepository.findById(productDto.getCategoryId());
        if (category.isEmpty())
            throw new ObjectNotFoundException(errorObjectWithNameNotFoundMessage("Category"));

        Discount discount = null;
        if (productDto.getDiscountId() != null) {
            discount = discountRepository.findById(productDto.getDiscountId()).get();
            if (discount == null)
                throw new ObjectNotFoundException(errorObjectWithNameNotFoundMessage("Discount"));
        }

        Product product = mapper.map(productDto, Product.class);
        product.setCategory(category.get());
        product.setDiscount(discount);
        product.setCreatedDate(LocalDateTime.now());

        return productRepository.save(product);
    }

    public Product getProduct(Long id) {
        Optional<Product> productOptional = productRepository.findById(id);
        if (productOptional.isEmpty())
            throw new ObjectNotFoundException(errorObjectNotFoundMessage);

        return productOptional.get();
    }

    public List<Product> getAllProducts(Integer pageNo, Integer pageSize, SortByProductEnum sortBy, SortDirEnum sortDir) {
        Pageable paging = PageRequest.of(pageNo, pageSize,
                sortDir == SortDirEnum.ASC ? Sort.by(sortBy.toString()).ascending()
                        : Sort.by(sortBy.toString()).descending());
        Page<Product> productPage = productRepository.findAll(paging);

        if (productPage.hasContent())
            return productPage.getContent();

        return new ArrayList<Product>();
    }

    public Product updateProduct(Long id, ProductDto productDto) {
        Optional<Product> productOptional = productRepository.findById(id);
        if (productOptional.isEmpty())
            throw new ObjectNotFoundException(errorObjectNotFoundMessage);

        Product product = productOptional.get();
        customerMapper.merge(productDto, product);

        if (productDto.getDiscountId() != null) {
            Optional<Discount> discountOptional = discountRepository.findById(productDto.getDiscountId());
            if (discountOptional.isEmpty())
                throw new ObjectNotFoundException(errorObjectWithNameNotFoundMessage("Discount"));

            product.setDiscount(discountOptional.get());
        }

        if (productDto.getCategoryId() != null) {
            Optional<Category> categoryOptional = categoryRepository.findById(productDto.getCategoryId());
            if (categoryOptional.isEmpty())
                throw new ObjectNotFoundException(errorObjectWithNameNotFoundMessage("Category"));

            product.setCategory(categoryOptional.get());
        }

        product.setModifiedDate(LocalDateTime.now());
        return productRepository.save(product);
    }

    public Map<Object, Object> deleteProduct(Long id) {
        Optional<Product> productOptional = productRepository.findById(id);
        if (productOptional.isEmpty())
            throw new ObjectNotFoundException(errorObjectNotFoundMessage);

        productRepository.deleteById(id);
        Map<Object, Object> model = new HashMap<>();
        model.put("id", id);
        model.put("success", true);
        return model;
    }
}
