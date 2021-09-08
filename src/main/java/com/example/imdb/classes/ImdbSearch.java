package com.example.imdb.classes;

import java.util.ArrayList;

public class ImdbSearch {
    private String searchType;
    private String expression;
    private ArrayList<ImdbResult> results;

    public String getSearchType() {
        return searchType;
    }

    public void setSearchType(String searchType) {
        this.searchType = searchType;
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public ArrayList<ImdbResult> getResults() {
        return results;
    }

    public void setResults(ArrayList<ImdbResult> results) {
        this.results = results;
    }
}
