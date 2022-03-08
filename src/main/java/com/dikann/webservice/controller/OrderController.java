package com.dikann.webservice.controller;

import com.dikann.webservice.dto.OrderDto;
import com.dikann.webservice.entity.Order;
import com.dikann.webservice.enums.OrderStatus;
import com.dikann.webservice.service.CartItemService;
import com.dikann.webservice.service.OrderService;
import com.dikann.webservice.utils.ApplicationConst;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(ApplicationConst.baseUrl + "order")
public class OrderController {

    private final OrderService orderService;
    private final CartItemService cartItemService;
    private final ModelMapper mapper;

    @Autowired
    public OrderController(OrderService orderService, CartItemService cartItemService, ModelMapper mapper) {
        this.orderService = orderService;
        this.cartItemService = cartItemService;
        this.mapper = mapper;

        this.mapper.typeMap(Order.class, OrderDto.class).addMapping(Order::getUserAddress, OrderDto::setUserAddressDto);
    }

    @PostMapping
    public ResponseEntity<OrderDto> addOrder(Principal user, @RequestBody @Valid OrderDto orderDto) {
        Order order = orderService.addOrder(user, orderDto);
        OrderDto orderDtoResponse = mapper.map(order, OrderDto.class);
        orderDtoResponse.setCartItems(cartItemService.getAllCartItemsByShoppingSessionId(order.getShoppingSession().getId()));
        return ResponseEntity.ok(orderDtoResponse);
    }

    @GetMapping("{id}")
    public ResponseEntity<OrderDto> getOrder(Principal user, @PathVariable("id") Long id) {
        Order order = orderService.getOrder(user, id);
        OrderDto orderDtoResponse = mapper.map(order, OrderDto.class);
        orderDtoResponse.setCartItems(cartItemService.getAllCartItemsByShoppingSessionId(order.getShoppingSession().getId()));
        return ResponseEntity.ok(orderDtoResponse);
    }

    @GetMapping
    public List<OrderDto> getAllOrders(Principal user) {
        return orderService.getAllOrders(user).stream().map(order -> mapper.map(order, OrderDto.class)).collect(Collectors.toList());
    }

    @PutMapping("{id}")
    public ResponseEntity<OrderDto> updateOrder(Principal user, @PathVariable(name = "id") Long id, @RequestParam(name = "order_status") @NotNull @Valid OrderStatus orderStatus) {
        Order order = orderService.updateOrder(user, id, orderStatus);
        OrderDto orderDtoResponse = mapper.map(order, OrderDto.class);
        orderDtoResponse.setCartItems(cartItemService.getAllCartItemsByShoppingSessionId(order.getShoppingSession().getId()));
        return ResponseEntity.ok(orderDtoResponse);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> deleteOrder(Principal user, @PathVariable("id") Long id) {
        return new ResponseEntity<Object>(orderService.deleteOrder(user, id), HttpStatus.OK);
    }
}
