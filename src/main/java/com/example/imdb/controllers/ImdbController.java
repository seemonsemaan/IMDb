package com.example.imdb.controllers;

import com.google.gson.Gson;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/search")
public class ImdbController {
    private static final String apiKey = "k_id8ki777";
    private static final String url = "https://imdb-api.com/en/API/";

    @GetMapping
    @RequestMapping("{value}")
    public ResponseEntity<String> searchByName(@PathVariable String value) {
        String requestUrl = url + "SearchAll/" + apiKey + "/" + value;
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(requestUrl).build();

        try (Response response = client.newCall(request).execute()) {
            return ResponseEntity.ok(response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Error calling IMDb");
        }
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