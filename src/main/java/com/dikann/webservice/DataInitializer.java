package com.dikann.webservice;

import com.dikann.webservice.config.AdminConfig;
import com.dikann.webservice.entity.Role;
import com.dikann.webservice.entity.User;
import com.dikann.webservice.enums.DefaultRoles;
import com.dikann.webservice.repository.RoleRepository;
import com.dikann.webservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AdminConfig adminConfig;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        initRoles();
        initAdmin();
    }

    private void initAdmin() {
        if (userRepository.findByUsername(adminConfig.getUsername()).isPresent())
            return;

        User admin = new User();
        admin.setEmail(adminConfig.getEmail());
        admin.setUsername(adminConfig.getUsername());
        admin.setPassword(passwordEncoder.encode(adminConfig.getPassword()));
        List<Role> roles = new ArrayList<>();
        for (DefaultRoles defaultRoles : DefaultRoles.values())
            roles.add(roleRepository.findByName(defaultRoles.toString()).get());

        admin.setRoles(roles);
        admin.setCreatedDate(LocalDateTime.now());
        admin.setFirstName("default");
        admin.setLastName("admin");
        userRepository.save(admin);
    }

    private void initRoles() {
        for (DefaultRoles defaultRoles : DefaultRoles.values()) {
            if (roleRepository.findByName(defaultRoles.toString()).isEmpty()) {
                Role role = new Role();
                role.setName(defaultRoles.toString());
                roleRepository.save(role);
            }
        }
    }
}
