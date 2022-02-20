package com.dikann.webservice.service;

import com.dikann.webservice.dto.DiscountDto;
import com.dikann.webservice.entity.Discount;
import com.dikann.webservice.entity.Discount;
import com.dikann.webservice.exception.ObjectNotFoundException;
import com.dikann.webservice.repository.DiscountRepository;
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
public class DiscountService {

    private final DiscountRepository discountRepository;
    private final CustomerMapper customerMapper;

    @Autowired
    public DiscountService(DiscountRepository discountRepository, CustomerMapper customerMapper) {
        this.discountRepository = discountRepository;
        this.customerMapper = customerMapper;
    }

    public Discount addDiscount(Discount discount) {
        discount.setCreatedDate(LocalDateTime.now());
        return discountRepository.save(discount);
    }

    public Discount getDiscount(Long id) {
        Optional<Discount> discountOptional = discountRepository.findById(id);
        if (discountOptional.isEmpty())
            throw new ObjectNotFoundException(errorObjectNoFoundMessage);

        return discountOptional.get();
    }

    public List<Discount> getAllCategories(Integer pageNo, Integer pageSize, String sortBy) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<Discount> discountPage = discountRepository.findAll(paging);

        if (discountPage.hasContent())
            return discountPage.getContent();

        return new ArrayList<Discount>();
    }

    public Discount updateDiscount(Long id, DiscountDto discountDto) {
        Optional<Discount> discountOptional = discountRepository.findById(id);
        if (discountOptional.isEmpty())
            throw new ObjectNotFoundException(errorObjectNoFoundMessage);

        Discount discount = discountOptional.get();
        customerMapper.merge(discountDto, discount);
        discount.setModifiedDate(LocalDateTime.now());
        return discountRepository.save(discount);
    }

    public Map<Object, Object> deleteDiscount(Long id) {
        Optional<Discount> discountOptional = discountRepository.findById(id);
        if (discountOptional.isEmpty())
            throw new ObjectNotFoundException(errorObjectNoFoundMessage);

        discountRepository.deleteById(id);
        Map<Object, Object> model = new HashMap<>();
        model.put("id", id);
        model.put("success", true);
        return model;
    }
}
