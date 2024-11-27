package com.example.anhvt86_3.domain.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ListMovie {
    @SerializedName("page")
    private int page;

    @SerializedName("results")
    private List<Movie> results;

    @SerializedName("total_pages")
    private int totalPages;

    @SerializedName("total_results")
    private int totalResults;

    public int getPage() { return page; }
    public List<Movie> getResults() { return results; }
    public int getTotalPages() { return totalPages; }
    public int getTotalResults() { return totalResults; }
}
