package com.dikann.webservice;

import com.dikann.webservice.entity.Role;
import com.dikann.webservice.enums.DefaultRoles;
import com.dikann.webservice.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {
        initRoles();
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
