package com.example.imdb.controllers;

import com.example.imdb.classes.LoginDTO;
import com.example.imdb.repositories.AuthRepository;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.*;

@RestController
@RequestMapping("auth")
public class authController {
    @Autowired
    AuthRepository authRepository;

    @PostMapping
    @RequestMapping("register")
    public ResponseEntity<String> register(@RequestBody LoginDTO login) {
        if (authRepository.checkUserExists(login)) {
            return ResponseEntity.badRequest().body("User already exists");
        }

        login.setPassword(authRepository.EncryptPassword(login.getPassword()));

        int res = authRepository.register(login);
        if (res > 0) {
            return ResponseEntity.ok("User registered");
        }
        if (res == -1) {
            return ResponseEntity.badRequest().body("Cannot create file");
        }
        if (res == -2) {
            return ResponseEntity.badRequest().body("Cannot read file");
        }
        if (res == -3) {
            return ResponseEntity.badRequest().body("Cannot write to file");
        }
        return ResponseEntity.badRequest().body("Unknown error");
    }

    @PostMapping
    @RequestMapping("login")
    public ResponseEntity<String> login(@RequestBody LoginDTO login) {
        if (!authRepository.checkUserExists(login)) {
            return ResponseEntity.badRequest().body("User does not exist");
        }

        login.setPassword(authRepository.EncryptPassword(login.getPassword()));

        int res = authRepository.login(login);
        if (res == 1)
            return ResponseEntity.ok("Correct credentials");
        if (res == 0)
            return ResponseEntity.badRequest().body("Wrong credentials");

        return ResponseEntity.badRequest().body("Unknown Error");

    }
}
