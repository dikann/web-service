package com.dikann.webservice.controller;

import com.dikann.webservice.dto.OrderDto;
import com.dikann.webservice.dto.ProductDto;
import com.dikann.webservice.entity.Order;
import com.dikann.webservice.entity.Product;
import com.dikann.webservice.service.OrderService;
import com.dikann.webservice.utils.ApplicationConst;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping(ApplicationConst.baseUrl + "order")
public class OrderController {

    private final OrderService orderService;
    private final ModelMapper mapper;

    @Autowired
    public OrderController(OrderService orderService, ModelMapper mapper) {
        this.orderService = orderService;
        this.mapper = mapper;

        this.mapper.typeMap(Order.class, OrderDto.class).addMapping(Order::getUserAddress, OrderDto::setUserAddressDto);
    }

    @PostMapping
    public ResponseEntity<OrderDto> addOrder(Principal user, @RequestBody @Valid OrderDto orderDto) {
        OrderDto orderDtoResponse = mapper.map(orderService.addOrder(user, orderDto), OrderDto.class);
        return ResponseEntity.ok(orderDtoResponse);
    }
}
