package com.dikann.webservice.controller;

import com.dikann.webservice.dto.LoginDto;
import com.dikann.webservice.service.AuthService;
import com.dikann.webservice.utils.ApplicationConst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping(ApplicationConst.baseUrl + "auth")
public class AuthController {
    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }


    @PostMapping("/login")
    public ResponseEntity signIn(@RequestBody @Valid LoginDto loginDto) {
        return ok(authService.signIn(loginDto.getUsername(), loginDto.getPassword()));
    }

}
