package com.example.imdb.repositories.impRepositories;

import com.example.imdb.classes.ImdbResult;
import com.example.imdb.classes.ImdbSearch;
import com.example.imdb.repositories.ImdbRepository;
import com.google.gson.Gson;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
public class ApiImdbRepository implements ImdbRepository {
    private static final String apiKey = "k_id8ki777";
    private static final String url = "https://imdb-api.com/en/API/";

    @Override
    public ImdbSearch searchByName(String value) throws Exception {
        String requestUrl = url + "SearchAll/" + apiKey + "/" + value;
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(requestUrl).build();
        Response response;
        try {
            response = client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
            throw new Exception("Error calling IMDb");
        }

        String responseString;
        try {
            responseString = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
            throw new Exception("Error reading response");
        }

        return new Gson().fromJson(responseString, ImdbSearch.class);
    }

    @Override
    public String getDetails(String value) throws Exception {
        String requestUrl = url + "Title/" + apiKey + "/" + value + "/FullActor,FullCast,Posters,Images,Trailer,Ratings,Wikipedia";
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(requestUrl).build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
            throw new Exception("Error calling IMDb");
        }
    }
}
