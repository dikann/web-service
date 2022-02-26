package com.dikann.webservice.controller;

import com.dikann.webservice.dto.UserAddressDto;
import com.dikann.webservice.entity.UserAddress;
import com.dikann.webservice.service.UserAddressService;
import com.dikann.webservice.utils.ApplicationConst;
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
@RequestMapping(ApplicationConst.baseUrl + "user-address")
public class UserAddressController {
    private final UserAddressService userAddressService;
    private final ModelMapper mapper;

    @Autowired
    public UserAddressController(UserAddressService userAddressService, ModelMapper mapper) {
        this.userAddressService = userAddressService;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity<UserAddressDto> addUserAddress(Principal userPrincipal, @RequestBody @Valid UserAddressDto userAddressDto) {
        UserAddress userAddress = mapper.map(userAddressDto, UserAddress.class);
        UserAddressDto userAddressDtoResponse = mapper.map(userAddressService.addUserAddress(userPrincipal, userAddress), UserAddressDto.class);
        return ResponseEntity.ok(userAddressDtoResponse);
    }

    @GetMapping("{id}")
    public ResponseEntity<UserAddressDto> getUserAddress(Principal userPrincipal, @PathVariable("id") Long id) {
        UserAddressDto userAddressDtoResponse = mapper.map(userAddressService.getUserAddress(userPrincipal, id), UserAddressDto.class);
        return ResponseEntity.ok(userAddressDtoResponse);
    }

    @GetMapping
    public List<UserAddressDto> getAllUserAddresses(Principal userPrincipal) {
        return userAddressService.getAllUserAddresses(userPrincipal).stream().map(userAddress -> mapper.map(userAddress, UserAddressDto.class))
                .collect(Collectors.toList());
    }

    @PutMapping("{id}")
    public ResponseEntity<UserAddressDto> updateUserAddress(Principal userPrincipal, @PathVariable("id") Long id, @RequestBody UserAddressDto userAddressDto) {
        UserAddressDto userAddressDtoResponse = mapper.map(userAddressService.updateUserAddress(userPrincipal, id, userAddressDto), UserAddressDto.class);
        return ResponseEntity.ok(userAddressDtoResponse);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> deleteUserAddress(Principal userPrincipal, @PathVariable("id") Long id) {
        return new ResponseEntity<Object>(userAddressService.deleteUserAddress(userPrincipal, id), HttpStatus.OK);
    }
}
