package com.example.imdb.controllers;

import com.example.imdb.classes.ImdbResult;
import com.example.imdb.classes.ImdbSearch;
import com.example.imdb.repositories.ImdbRepository;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/search")
public class ImdbController {
    @Autowired
    ImdbRepository imdb;

    @GetMapping
    @RequestMapping({"{value}","{value}/{top}"})
    public ResponseEntity<String> searchByName(@PathVariable String value, @PathVariable Optional<Integer> top) {
        ImdbSearch item;
        try{
            item = imdb.searchByName(value);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

        if(top.isPresent() && top.get() > 0) {

            ArrayList<ImdbResult> list = item.getResults();

            ArrayList<ImdbResult> firstX = (ArrayList<ImdbResult>) list.stream().limit(top.get()).collect(Collectors.toList());

            item.setResults(firstX);

            return ResponseEntity.ok(new Gson().toJson(item));
        }
        return ResponseEntity.ok(new Gson().toJson(item));
    }

    @GetMapping
    @RequestMapping("info/{value}")
    public ResponseEntity<String> getDetails(@PathVariable String value) {
        String res;
        try {
            res = imdb.getDetails(value);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok(res);
    }
}