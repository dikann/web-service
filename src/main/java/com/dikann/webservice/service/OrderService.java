package com.dikann.webservice.service;

import com.dikann.webservice.dto.OrderDto;
import com.dikann.webservice.entity.*;
import com.dikann.webservice.enums.OrderStatus;
import com.dikann.webservice.exception.ObjectNotFoundException;
import com.dikann.webservice.repository.OrderRepository;
import com.dikann.webservice.utils.ApplicationConst;
import com.dikann.webservice.utils.CartItemUtils;
import com.dikann.webservice.utils.CustomerMapper;
import com.dikann.webservice.utils.UserUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserAddressService userAddressService;
    private final ShoppingSessionService shoppingSessionService;
    private final CartItemService cartItemService;
    private final UserUtils userUtils;
    private final ModelMapper mapper;
    private final CustomerMapper customerMapper;

    @Autowired
    public OrderService(OrderRepository orderRepository, UserAddressService userAddressService, ShoppingSessionService shoppingSessionService, CartItemService cartItemService, UserUtils userUtils, ModelMapper mapper, CustomerMapper customerMapper) {
        this.orderRepository = orderRepository;
        this.userAddressService = userAddressService;
        this.shoppingSessionService = shoppingSessionService;
        this.cartItemService = cartItemService;
        this.userUtils = userUtils;
        this.mapper = mapper;
        this.customerMapper = customerMapper;
    }

    public Order addOrder(Principal userPrincipal, OrderDto orderDto) {
        UserAddress userAddress = userAddressService.getUserAddress(userPrincipal, orderDto.getUserAddressId());
        ShoppingSession shoppingSession = shoppingSessionService.getActiveShoppingSession(userUtils.getUser(userPrincipal));
        List<CartItem> cartItems = cartItemService.getAllCartItems(userPrincipal, true);

        if (cartItems.isEmpty())
            throw new ObjectNotFoundException(ApplicationConst.errorObjectWithNameNotFoundMessage("user cart"));

        Order order = mapper.map(orderDto, Order.class);
        order.setShoppingSession(shoppingSession);
        order.setUserAddress(userAddress);
        order.setTotalDiscount(CartItemUtils.totalDiscount(cartItems));
        order.setCreatedDate(LocalDateTime.now());

//        shoppingSession.setValid(false);
//        shoppingSessionService.updateShoppingSession(shoppingSession.getId(), shoppingSession);
        return orderRepository.save(order);
    }

    public Order getOrder(Principal userPrincipal, Long id) {
        User user = userUtils.getUser(userPrincipal);
        Optional<Order> orderOptional = orderRepository.findByIdAndUserId(user.getId(), id);
        if (orderOptional.isEmpty())
            throw new ObjectNotFoundException(ApplicationConst.errorObjectWithNameNotFoundMessage("order"));

        return orderOptional.get();
    }

    public List<Order> getAllOrders(Principal usPrincipal) {
        User user = userUtils.getUser(usPrincipal);

        List<Order> orders = orderRepository.findAllByUserId(user.getId());

        if (!orders.isEmpty())
            return orders;

        return new ArrayList<>();
    }

    public Order updateOrder(Principal userPrincipal, Long id, OrderStatus orderStatus) {
        Order order = getOrder(userPrincipal, id);

        order.setOrderStatus(orderStatus);
        return orderRepository.save(order);
    }

    public Map<Object, Object> deleteOrder(Principal userPrincipal, Long id) {
        Order order = getOrder(userPrincipal, id);
        orderRepository.deleteById(order.getId());
        Map<Object, Object> model = new HashMap<>();
        model.put("id", id);
        model.put("success", true);
        return model;
    }

}
