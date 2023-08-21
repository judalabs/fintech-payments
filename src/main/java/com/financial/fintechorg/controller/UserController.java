package com.financial.fintechorg.controller;

import java.math.BigDecimal;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.financial.fintechorg.dto.UserDTO;
import com.financial.fintechorg.service.UserService;

import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
@Slf4j
@Api("Handles users from UserType")
public class UserController {

    private final UserService userService;

    @GetMapping("/balance/{userId}")
    public ResponseEntity<BigDecimal> getBalance(@PathVariable UUID userId) {
        log.debug("start GET balance user {}", userId);
        return ResponseEntity.ok(userService.getBalance(userId));
    }

    @PostMapping
    public ResponseEntity<UUID> save(@RequestBody UserDTO user) {
        log.debug("start POST new user");
        return ResponseEntity.ok(userService.save(user).getUuid());
    }
}
