package com.example.imdb.repositories;

import com.example.imdb.classes.ImdbSearch;

public interface ImdbRepository {
    ImdbSearch searchByName(String value) throws Exception;
    String getDetails(String value) throws Exception;
}
