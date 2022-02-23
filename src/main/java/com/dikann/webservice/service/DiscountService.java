package com.dikann.webservice.service;

import com.dikann.webservice.dto.DiscountDto;
import com.dikann.webservice.entity.Discount;
import com.dikann.webservice.enums.SortByEnum;
import com.dikann.webservice.enums.SortDirEnum;
import com.dikann.webservice.exception.ObjectNotFoundException;
import com.dikann.webservice.repository.DiscountRepository;
import com.dikann.webservice.repository.ProductRepository;
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
public class DiscountService {

    private final DiscountRepository discountRepository;
    private final CustomerMapper customerMapper;
    private final ProductRepository productRepository;

    @Autowired
    public DiscountService(DiscountRepository discountRepository, CustomerMapper customerMapper, ProductRepository productRepository) {
        this.discountRepository = discountRepository;
        this.customerMapper = customerMapper;
        this.productRepository = productRepository;
    }

    public Discount addDiscount(Discount discount) {
        discount.setCreatedDate(LocalDateTime.now());
        return discountRepository.save(discount);
    }

    public Discount getDiscount(Long id) {
        Optional<Discount> discountOptional = discountRepository.findById(id);
        if (discountOptional.isEmpty())
            throw new ObjectNotFoundException(errorObjectNotFoundMessage);

        return discountOptional.get();
    }

    public List<Discount> getAllDiscounts(Integer pageNo, Integer pageSize, SortByEnum sortBy, SortDirEnum sortDir) {
        Pageable paging = PageRequest.of(pageNo, pageSize,
                sortDir == SortDirEnum.ASC ? Sort.by(sortBy.toString()).ascending()
                        : Sort.by(sortBy.toString()).descending());
        Page<Discount> discountPage = discountRepository.findAll(paging);

        if (discountPage.hasContent())
            return discountPage.getContent();

        return new ArrayList<Discount>();
    }

    public Discount updateDiscount(Long id, DiscountDto discountDto) {
        Optional<Discount> discountOptional = discountRepository.findById(id);
        if (discountOptional.isEmpty())
            throw new ObjectNotFoundException(errorObjectNotFoundMessage);

        Discount discount = discountOptional.get();
        customerMapper.merge(discountDto, discount);
        discount.setModifiedDate(LocalDateTime.now());
        return discountRepository.save(discount);
    }

    public Map<Object, Object> deleteDiscount(Long id) {
        Optional<Discount> discountOptional = discountRepository.findById(id);
        if (discountOptional.isEmpty())
            throw new ObjectNotFoundException(errorObjectNotFoundMessage);

        productRepository.deleteByDiscountId(id);

        discountRepository.deleteById(id);
        Map<Object, Object> model = new HashMap<>();
        model.put("id", id);
        model.put("success", true);
        return model;
    }
}
