package com.dikann.webservice.service;

import com.dikann.webservice.entity.ShoppingSession;
import com.dikann.webservice.entity.User;
import com.dikann.webservice.repository.ShoppingSessionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ShoppingSessionService {
    private final ShoppingSessionRepository shoppingSessionRepository;

    public ShoppingSessionService(ShoppingSessionRepository shoppingSessionRepository) {
        this.shoppingSessionRepository = shoppingSessionRepository;
    }

    public ShoppingSession getActiveShoppingSession(User user) {
        Optional<ShoppingSession> shoppingSessionOptional = shoppingSessionRepository.getActiveShoppingSession(user.getId());
        if (shoppingSessionRepository.getActiveShoppingSession(user.getId()).isPresent())
            return shoppingSessionOptional.get();

        ShoppingSession shoppingSession = new ShoppingSession();
        shoppingSession.setCreatedDate(LocalDateTime.now());
        shoppingSession.setUser(user);
        shoppingSession.setValid(true);
        return shoppingSessionRepository.save(shoppingSession);
    }
}
