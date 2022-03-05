package com.dikann.webservice.service;

import com.dikann.webservice.dto.CartItemDto;
import com.dikann.webservice.entity.*;
import com.dikann.webservice.enums.SortByCartItemEnum;
import com.dikann.webservice.enums.SortDirEnum;
import com.dikann.webservice.exception.ObjectNotFoundException;
import com.dikann.webservice.repository.CartItemRepository;
import com.dikann.webservice.repository.ShoppingSessionRepository;
import com.dikann.webservice.utils.CustomerMapper;
import com.dikann.webservice.utils.UserUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.*;

import static com.dikann.webservice.utils.ApplicationConst.errorObjectNotFoundMessage;

@Service
public class CartItemService {

    private final CartItemRepository cartItemRepository;
    private final ShoppingSessionRepository shoppingSessionRepository;
    private final ShoppingSessionService shoppingSessionService;
    private final ProductService productService;
    private final ModelMapper mapper;
    private final CustomerMapper customerMapper;
    private final UserUtils userUtils;

    @Autowired
    public CartItemService(CartItemRepository cartItemRepository, ShoppingSessionRepository shoppingSessionRepository, ShoppingSessionRepository shoppingSessionRepository1, ShoppingSessionService shoppingSessionService, ProductService productService, ModelMapper mapper, CustomerMapper customerMapper, UserUtils userUtils) {
        this.cartItemRepository = cartItemRepository;
        this.shoppingSessionRepository = shoppingSessionRepository1;
        this.shoppingSessionService = shoppingSessionService;
        this.productService = productService;
        this.mapper = mapper;
        this.customerMapper = customerMapper;
        this.userUtils = userUtils;
    }

    public CartItem addCartItem(Principal userPrincipal, CartItemDto cartItemDto) {
        User user = userUtils.getUser(userPrincipal);
        ShoppingSession shoppingSession = shoppingSessionService.getActiveShoppingSession(user);
        Product product = productService.getProduct(cartItemDto.getProductId());

        CartItem cartItem = new CartItem();
        cartItem = mapper.map(cartItemDto, CartItem.class);
        cartItem.setProduct(product);
        cartItem.setShoppingSession(shoppingSession);
        cartItem.setCreatedDate(LocalDateTime.now());

        return cartItemRepository.save(cartItem);
    }

    public CartItem getCartItem(Principal userPrincipal, Long id) {
        User user = userUtils.getUser(userPrincipal);
        Optional<CartItem> cartItemOptional = cartItemRepository.findByIdAndUserId(id, user.getId());
        if (cartItemOptional.isEmpty())
            throw new ObjectNotFoundException(errorObjectNotFoundMessage);

        System.out.println(cartItemOptional.get().getProduct().getName());
        return cartItemOptional.get();
    }

    public List<CartItem> getAllCartItems(Principal userPrincipal, boolean valid) {
        User user = userUtils.getUser(userPrincipal);

        List<CartItem> cartItemList = cartItemRepository.findAllByUserIdAndValid(user.getId(), valid);

        if (!cartItemList.isEmpty())
            return cartItemList;

        return new ArrayList<CartItem>();
    }

    public CartItem updateCartItem(Principal userPrincipal, Long id, CartItemDto cartItemDto) {
        User user = userUtils.getUser(userPrincipal);
        Optional<CartItem> cartItemOptional = cartItemRepository.findByIdAndUserId(id, user.getId());
        if (cartItemOptional.isEmpty())
            throw new ObjectNotFoundException(errorObjectNotFoundMessage);

        CartItem cartItem = cartItemOptional.get();
        customerMapper.merge(cartItemDto, cartItem);

        if (cartItemDto.getProductId() != null) {
            Product product = productService.getProduct(cartItemDto.getProductId());
            cartItem.setProduct(product);
        }

        cartItem.setModifiedDate(LocalDateTime.now());
        return cartItemRepository.save(cartItem);
    }

    public Map<Object, Object> deleteCartItem(Principal userPrincipal, Long id) {
        User user = userUtils.getUser(userPrincipal);
        Optional<CartItem> cartItemOptional = cartItemRepository.findByIdAndUserId(id, user.getId());
        if (cartItemOptional.isEmpty())
            throw new ObjectNotFoundException(errorObjectNotFoundMessage);

        cartItemRepository.deleteById(id);
        Map<Object, Object> model = new HashMap<>();
        model.put("id", id);
        model.put("success", true);
        return model;
    }
}
