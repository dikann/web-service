package com.dikann.webservice.controller;

import com.dikann.webservice.dto.SignUpDto;
import com.dikann.webservice.entity.Role;
import com.dikann.webservice.entity.User;
import com.dikann.webservice.enums.SortByUserEnum;
import com.dikann.webservice.enums.SortDirEnum;
import com.dikann.webservice.service.UserService;
import com.dikann.webservice.utils.ApplicationConst;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(ApplicationConst.baseUrl + "user")
public class UserController {

    private final UserService userService;
    private final ModelMapper mapper;

    @Autowired
    public UserController(UserService userService, ModelMapper mapper) {
        this.userService = userService;
        this.mapper = mapper;

        this.mapper.typeMap(User.class, SignUpDto.class).addMappings(skipper -> skipper.skip(SignUpDto::setRoles));
    }

    @PostMapping
    public ResponseEntity<SignUpDto> addUser(@RequestBody @Valid SignUpDto signUpDto) {
        return ResponseEntity.ok(getSignupDto(userService.addUser(signUpDto)));
    }

    @GetMapping("{id}")
    public ResponseEntity<SignUpDto> getUser(@PathVariable("id") Long id) {
        return ResponseEntity.ok(getSignupDto(userService.getUser(id)));
    }

    @GetMapping
    public List<SignUpDto> getAllUsers(@RequestParam(defaultValue = ApplicationConst.pageNo, name = "page") Integer pageNo,
                                       @RequestParam(defaultValue = ApplicationConst.pageSize, name = "page_size") Integer pageSize,
                                       @RequestParam(defaultValue = ApplicationConst.sortBy, name = "sort_by") SortByUserEnum sortBy,
                                       @RequestParam(defaultValue = ApplicationConst.sortDir, name = "sort_dir") SortDirEnum sortDir) {

        return userService.getAllUsers(pageNo, pageSize, sortBy, sortDir).stream().map(user -> getSignupDto(user))
                .collect(Collectors.toList());
    }

    @PutMapping("{id}")
    public ResponseEntity<SignUpDto> updateUser(@PathVariable("id") Long id, @RequestBody SignUpDto signUpDto) {
        return ResponseEntity.ok(getSignupDto(userService.updateUser(id, signUpDto)));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable("id") Long id) {
        return new ResponseEntity<Object>(userService.deleteUser(id), HttpStatus.OK);
    }

    public SignUpDto getSignupDto(User user) {
        SignUpDto signUpDtoResponse = mapper.map(user, SignUpDto.class);
        signUpDtoResponse.setRoles(roleStringFromRole(user.getRoles()));
        return signUpDtoResponse;
    }

    public List<String> roleStringFromRole(List<Role> roleList) {
        List<String> roles = new ArrayList<>();
        for (Role role : roleList)
            roles.add(role.getName());

        return roles;
    }
}
