package com.dikann.webservice.service;

import com.dikann.webservice.entity.ShoppingSession;
import com.dikann.webservice.entity.User;
import com.dikann.webservice.exception.ObjectNotFoundException;
import com.dikann.webservice.repository.ShoppingSessionRepository;
import com.dikann.webservice.utils.ApplicationConst;
import com.dikann.webservice.utils.CustomerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ShoppingSessionService {
    private final ShoppingSessionRepository shoppingSessionRepository;
    private final CustomerMapper customerMapper;

    @Autowired
    public ShoppingSessionService(ShoppingSessionRepository shoppingSessionRepository, CustomerMapper customerMapper) {
        this.shoppingSessionRepository = shoppingSessionRepository;
        this.customerMapper = customerMapper;
    }

    public ShoppingSession getActiveShoppingSession(User user) {
        Optional<ShoppingSession> shoppingSessionOptional = shoppingSessionRepository.getActiveShoppingSession(user.getId());
        if (shoppingSessionOptional.isPresent())
            return shoppingSessionOptional.get();

        return addShoppingSession(user);
    }

    public ShoppingSession getShoppingSession(Long id) {
        Optional<ShoppingSession> shoppingSessionOptional = shoppingSessionRepository.findById(id);
        if (shoppingSessionOptional.isEmpty())
            throw new ObjectNotFoundException(ApplicationConst.errorObjectWithNameNotFoundMessage("shopping session"));

        return shoppingSessionOptional.get();
    }

    public ShoppingSession addShoppingSession(User user) {
        ShoppingSession shoppingSession = new ShoppingSession();
        shoppingSession.setCreatedDate(LocalDateTime.now());
        shoppingSession.setUser(user);
        shoppingSession.setValid(true);
        return shoppingSessionRepository.save(shoppingSession);
    }

    public ShoppingSession updateShoppingSession(Long id, ShoppingSession shoppingSession) {
        Optional<ShoppingSession> shoppingSessionOptional = shoppingSessionRepository.findById(id);
        if (shoppingSessionOptional.isEmpty())
            throw new ObjectNotFoundException(ApplicationConst.errorObjectWithNameNotFoundMessage("shopping session"));

        ShoppingSession shoppingSessionResult = shoppingSessionOptional.get();
        customerMapper.merge(shoppingSessionResult, shoppingSession);
        shoppingSessionResult.setModifiedDate(LocalDateTime.now());
        return shoppingSessionRepository.save(shoppingSessionResult);
    }
}
