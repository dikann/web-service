package com.dikann.webservice.utils;

import com.dikann.webservice.entity.User;
import com.dikann.webservice.exception.ObjectNotFoundException;
import com.dikann.webservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.security.Principal;
import java.util.Optional;

@Component
public class UserUtils {
    private final UserRepository userRepository;

    @Autowired
    public UserUtils(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUser(Principal user) {
        Optional<User> userOptional = userRepository.findByUsername(user.getName());
        if (userOptional.isEmpty())
            throw new ObjectNotFoundException(ApplicationConst.errorObjectWithNameNotFoundMessage("user"));

        return userOptional.get();
    }
}
