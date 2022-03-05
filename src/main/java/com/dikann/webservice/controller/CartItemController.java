package com.dikann.webservice.controller;

import com.dikann.webservice.dto.CartItemDto;
import com.dikann.webservice.entity.CartItem;
import com.dikann.webservice.enums.SortByCartItemEnum;
import com.dikann.webservice.enums.SortDirEnum;
import com.dikann.webservice.service.CartItemService;
import com.dikann.webservice.utils.ApplicationConst;
import com.dikann.webservice.views.Views;
import com.fasterxml.jackson.annotation.JsonView;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(ApplicationConst.baseUrl + "cart")
public class CartItemController {

    private final CartItemService cartItemService;
    private final ModelMapper mapper;

    @Autowired
    public CartItemController(CartItemService cartItemService, ModelMapper mapper) {
        this.cartItemService = cartItemService;
        this.mapper = mapper;

        this.mapper.typeMap(CartItem.class, CartItemDto.class).addMapping(CartItem::getProduct, CartItemDto::setProductDto);
    }

    @PostMapping
    public ResponseEntity<CartItemDto> addCartItem(Principal user, @RequestBody @Valid CartItemDto cartItemDto) {
        CartItemDto cartItemDtoResponse = mapper.map(cartItemService.addCartItem(user, cartItemDto), CartItemDto.class);
        return ResponseEntity.ok(cartItemDtoResponse);
    }

    @JsonView(value = {Views.Summery.class})
    @GetMapping("{id}")
    public ResponseEntity<CartItemDto> getCartItem(Principal user, @PathVariable(name = "id") Long id) {
        CartItemDto cartItemDtoResponse = mapper.map(cartItemService.getCartItem(user, id), CartItemDto.class);
        return ResponseEntity.ok(cartItemDtoResponse);
    }

    @JsonView(value = {Views.Summery.class})
    @GetMapping
    public List<CartItemDto> getAllCartItems(Principal user,
                                             @RequestParam(defaultValue = "true", name = "valid_session") boolean valid) {
        return cartItemService.getAllCartItems(user, valid).stream().map(cartItem -> mapper.map(cartItem, CartItemDto.class))
                .collect(Collectors.toList());
    }

    @PutMapping("{id}")
    public ResponseEntity<CartItemDto> updateCartItem(Principal user, @PathVariable(name = "id") Long id, @RequestBody CartItemDto cartItemDto) {
        CartItemDto cartItemDtoResponse = mapper.map(cartItemService.updateCartItem(user, id, cartItemDto), CartItemDto.class);
        return ResponseEntity.ok(cartItemDtoResponse);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> deleteCartItem(Principal user, @PathVariable(name = "id") Long id) {
        return new ResponseEntity<Object>(cartItemService.deleteCartItem(user, id), HttpStatus.OK);
    }
}
