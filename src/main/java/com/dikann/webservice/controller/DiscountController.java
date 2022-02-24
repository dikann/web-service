package com.dikann.webservice.controller;

import com.dikann.webservice.dto.DiscountDto;
import com.dikann.webservice.entity.Discount;
import com.dikann.webservice.enums.SortByEnum;
import com.dikann.webservice.enums.SortDirEnum;
import com.dikann.webservice.service.DiscountService;
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
@RequestMapping(ApplicationConst.baseUrl + "discount")
@JsonView(value = {Views.Detailed.class})
public class DiscountController {
    private final DiscountService discountService;
    private final ModelMapper mapper;

    @Autowired
    public DiscountController(DiscountService discountService, ModelMapper mapper) {
        this.discountService = discountService;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity<DiscountDto> addDiscount(@RequestBody @Valid DiscountDto discountDto) {
        Discount discount = mapper.map(discountDto, Discount.class);
        DiscountDto discountDtoResponse = mapper.map(discountService.addDiscount(discount), DiscountDto.class);
        return ResponseEntity.ok(discountDtoResponse);
    }

    @GetMapping("{id}")
    public ResponseEntity<DiscountDto> getDiscount(@PathVariable("id") Long id) {
        DiscountDto discountDto = mapper.map(discountService.getDiscount(id), DiscountDto.class);
        return ResponseEntity.ok(discountDto);
    }

    @GetMapping
    public List<DiscountDto> getAllDiscounts(@RequestParam(defaultValue = ApplicationConst.pageNo, name = "page") Integer pageNo,
                                             @RequestParam(defaultValue = ApplicationConst.pageSize, name = "page_size") Integer pageSize,
                                             @RequestParam(defaultValue = ApplicationConst.sortBy, name = "sort_by") SortByEnum sortBy,
                                             @RequestParam(defaultValue = ApplicationConst.sortDir, name = "sort_dir") SortDirEnum sortDir) {

        return discountService.getAllDiscounts(pageNo, pageSize, sortBy, sortDir).stream().map(discount -> mapper.map(discount, DiscountDto.class))
                .collect(Collectors.toList());
    }

    @PutMapping("{id}")
    public ResponseEntity<DiscountDto> updateDiscount(@PathVariable("id") Long id, @RequestBody DiscountDto discountDto) {
        DiscountDto discountDtoResponse = mapper.map(discountService.updateDiscount(id, discountDto), DiscountDto.class);
        return ResponseEntity.ok(discountDtoResponse);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> deleteDiscount(@PathVariable("id") Long id,
                                                 @RequestParam(defaultValue = "false", name = "delete_products") boolean deleteProducts) {
        return new ResponseEntity<Object>(discountService.deleteDiscount(id, deleteProducts), HttpStatus.OK);
    }
}
