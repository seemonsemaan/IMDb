package com.example.imdb.controllers;

import com.example.imdb.classes.ImdbResult;
import com.example.imdb.classes.ImdbSearch;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;
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
    private static final String apiKey = "k_id8ki777";
    private static final String url = "https://imdb-api.com/en/API/";

    @GetMapping
    @RequestMapping({"{value}","{value}/{top}"})
    public ResponseEntity<String> searchByName(@PathVariable String value, @PathVariable Optional<Integer> top) {
        String requestUrl = url + "SearchAll/" + apiKey + "/" + value;
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(requestUrl).build();
        Response response;
        try {
            response = client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Error calling IMDb");
        }

        String responseString = "";
        try {
            responseString = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Error reading response");
        }
        if(top.isPresent() && top.get() > 0) {
            ImdbSearch item = new Gson().fromJson(responseString, ImdbSearch.class);
            ArrayList<ImdbResult> list = item.getResults();

            ArrayList<ImdbResult> firstX = (ArrayList<ImdbResult>) list.stream().limit(top.get()).collect(Collectors.toList());

            item.setResults(firstX);

            return ResponseEntity.ok(new Gson().toJson(item));
        }

        return ResponseEntity.ok(responseString);

    }

    @GetMapping
    @RequestMapping("info/{value}")
    public ResponseEntity<String> getDetails(@PathVariable String value) {
        String requestUrl = url + "Title/" + apiKey + "/" + value + "/FullActor,FullCast,Posters,Images,Trailer,Ratings,Wikipedia";
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(requestUrl).build();

        try (Response response = client.newCall(request).execute()) {
            return ResponseEntity.ok(response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Error calling IMDb");
        }
    }
}