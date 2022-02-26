package com.dikann.webservice.service;

import com.dikann.webservice.dto.SignUpDto;
import com.dikann.webservice.entity.Role;
import com.dikann.webservice.entity.User;
import com.dikann.webservice.enums.DefaultRoles;
import com.dikann.webservice.enums.SortByUserEnum;
import com.dikann.webservice.enums.SortDirEnum;
import com.dikann.webservice.exception.ObjectNotFoundException;
import com.dikann.webservice.repository.RoleRepository;
import com.dikann.webservice.repository.UserRepository;
import com.dikann.webservice.utils.ApplicationConst;
import com.dikann.webservice.utils.CustomerMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

import static com.dikann.webservice.utils.ApplicationConst.errorObjectNotFoundMessage;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ModelMapper mapper;
    private final CustomerMapper customerMapper;

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository, ModelMapper mapper, CustomerMapper customerMapper) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.mapper = mapper;
        this.customerMapper = customerMapper;

        this.mapper.typeMap(SignUpDto.class, User.class).addMappings(skipper -> skipper.skip(User::setRoles));
    }

    public User addUser(SignUpDto signUpDto) {
        if (isEmailTaken(signUpDto.getEmail()))
            throw new ObjectNotFoundException(ApplicationConst.errorObjectTakenCodeMessage("Email"));
        if (isUsernameTaken(signUpDto.getUsername()))
            throw new ObjectNotFoundException(ApplicationConst.errorObjectTakenCodeMessage("Username"));

        User user = mapper.map(signUpDto, User.class);
        user.setRoles(roleFromString(signUpDto.getRoles()));
        user.setCreatedDate(LocalDateTime.now());
        return userRepository.save(user);
    }

    public User getUser(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty())
            throw new ObjectNotFoundException(ApplicationConst.errorObjectNotFoundMessage);

        return user.get();
    }

    public List<User> getAllUsers(Integer pageNo, Integer pageSize, SortByUserEnum sortBy, SortDirEnum sortDir) {
        Pageable paging = PageRequest.of(pageNo, pageSize,
                sortDir == SortDirEnum.ASC ? Sort.by(sortBy.toString()).ascending()
                        : Sort.by(sortBy.toString()).descending());
        Page<User> userPage = userRepository.findAll(paging);

        if (userPage.hasContent())
            return userPage.getContent();

        return new ArrayList<User>();
    }

    public User updateUser(Long id, SignUpDto signUpDto) {
        if (isEmailTaken(signUpDto.getEmail()))
            throw new ObjectNotFoundException(ApplicationConst.errorObjectTakenCodeMessage("Email"));
        if (isUsernameTaken(signUpDto.getUsername()))
            throw new ObjectNotFoundException(ApplicationConst.errorObjectTakenCodeMessage("Username"));

        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isEmpty())
            throw new ObjectNotFoundException(ApplicationConst.errorObjectNotFoundMessage);

        User user = userOptional.get();
        customerMapper.merge(signUpDto, user);
        if (!signUpDto.getRoles().isEmpty())
            user.setRoles(roleFromString(signUpDto.getRoles()));
        user.setModifiedDate(LocalDateTime.now());
        return userRepository.save(user);
    }

    public Map<Object, Object> deleteUser(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isEmpty())
            throw new ObjectNotFoundException(errorObjectNotFoundMessage);

        userRepository.deleteById(id);
        Map<Object, Object> model = new HashMap<>();
        model.put("id", id);
        model.put("success", true);
        return model;
    }

    public List<Role> roleFromString(List<String> stringList) {
        List<Role> roles = new ArrayList<>();
        for (String name : stringList) {
            Optional<Role> role = roleRepository.findByName(name);
            if (role.isEmpty())
                throw new ObjectNotFoundException(ApplicationConst.errorObjectWithNameNotFoundMessage(name));

            roles.add(role.get());
        }

        if (roles.isEmpty())
            roles.add(roleRepository.findByName(DefaultRoles.USER.toString()).get());

        return roles;
    }

    private boolean isUsernameTaken(String username) {
        return userRepository.findByUsername(username).isEmpty();
    }

    private boolean isEmailTaken(String email) {
        return userRepository.findByEmail(email).isEmpty();
    }

}
