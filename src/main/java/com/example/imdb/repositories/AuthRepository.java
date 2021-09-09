package com.example.imdb.repositories;

import com.example.imdb.classes.LoginDTO;

public interface AuthRepository {
    int register(LoginDTO login);
    int login(LoginDTO login);
    LoginDTO getCurrentUser();
    void removeCurrentUser();
    boolean checkUserExists(LoginDTO login);
    String EncryptPassword(String password);
}
