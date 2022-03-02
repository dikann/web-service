package com.dikann.webservice.service;

import com.dikann.webservice.dto.UserAddressDto;
import com.dikann.webservice.entity.User;
import com.dikann.webservice.entity.UserAddress;
import com.dikann.webservice.exception.ObjectNotFoundException;
import com.dikann.webservice.repository.UserAddressRepository;
import com.dikann.webservice.repository.UserRepository;
import com.dikann.webservice.utils.ApplicationConst;
import com.dikann.webservice.utils.CustomerMapper;
import com.dikann.webservice.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UserAddressService {
    private final UserAddressRepository userAddressRepository;
    private final UserRepository userRepository;
    private final CustomerMapper customerMapper;
    private final UserUtils userUtils;

    @Autowired
    public UserAddressService(UserAddressRepository userAddressRepository, UserRepository userRepository, CustomerMapper customerMapper, UserUtils userUtils) {
        this.userAddressRepository = userAddressRepository;
        this.userRepository = userRepository;
        this.customerMapper = customerMapper;
        this.userUtils = userUtils;
    }

    public UserAddress addUserAddress(Principal userPrincipal, UserAddress userAddress) {
        User user = userUtils.getUser(userPrincipal);
        userAddress.setUserId(user.getId());
        return userAddressRepository.save(userAddress);
    }

    public UserAddress getUserAddress(Principal userPrincipal, Long id) {
        User user = userUtils.getUser(userPrincipal);
        Optional<UserAddress> userAddressOptional = userAddressRepository.findByIdAndUserId(id, user.getId());
        if (userAddressOptional.isEmpty())
            throw new ObjectNotFoundException(ApplicationConst.errorObjectWithNameNotFoundMessage("user address"));

        return userAddressOptional.get();
    }

    public List<UserAddress> getAllUserAddresses(Principal userPrincipal) {
        User user = userUtils.getUser(userPrincipal);
        return userAddressRepository.findAllByUserId(user.getId());
    }

    public UserAddress updateUserAddress(Principal userPrincipal, Long id, UserAddressDto userAddressDto) {
        User user = userUtils.getUser(userPrincipal);
        Optional<UserAddress> userAddressOptional = userAddressRepository.findByIdAndUserId(id, user.getId());
        if (userAddressOptional.isEmpty())
            throw new ObjectNotFoundException(ApplicationConst.errorObjectWithNameNotFoundMessage("user address"));

        UserAddress userAddress = userAddressOptional.get();
        customerMapper.merge(userAddress, userAddressDto);
        return userAddressRepository.save(userAddress);
    }

    public Map<Object, Object> deleteUserAddress(Principal userPrincipal, Long id) {
        User user = userUtils.getUser(userPrincipal);
        Optional<UserAddress> userAddressOptional = userAddressRepository.findByIdAndUserId(id, user.getId());
        if (userAddressOptional.isEmpty())
            throw new ObjectNotFoundException(ApplicationConst.errorObjectWithNameNotFoundMessage("user address"));

        userAddressRepository.deleteByIdAndUserId(id, user.getId());
        Map<Object, Object> model = new HashMap<>();
        model.put("id", id);
        model.put("success", true);
        return model;
    }
}
