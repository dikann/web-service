package com.dikann.webservice.service;

import com.dikann.webservice.dto.OrderDto;
import com.dikann.webservice.entity.CartItem;
import com.dikann.webservice.entity.Order;
import com.dikann.webservice.entity.ShoppingSession;
import com.dikann.webservice.entity.UserAddress;
import com.dikann.webservice.exception.ObjectNotFoundException;
import com.dikann.webservice.repository.OrderRepository;
import com.dikann.webservice.utils.ApplicationConst;
import com.dikann.webservice.utils.CartItemUtils;
import com.dikann.webservice.utils.CustomerMapper;
import com.dikann.webservice.utils.UserUtils;
import org.aspectj.weaver.ast.Or;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

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

        shoppingSession.setValid(false);
        shoppingSessionService.updateShoppingSession(shoppingSession.getId(), shoppingSession);
        return orderRepository.save(order);
    }

}
