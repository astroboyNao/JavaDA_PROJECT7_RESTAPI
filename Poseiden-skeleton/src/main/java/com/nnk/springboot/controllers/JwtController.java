package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.services.JwtService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * The type Rating controller.
 */
@RestController
@AllArgsConstructor
public class JwtController {
    private final JwtService jwtService;

    @PostMapping("/authenticate")
    public String createJwtToken(@RequestBody User user) throws Exception {
        return jwtService.authenticate(user);
    }
}
