package com.example.anhvt86_3.domain.model;

public class Settings {
    private String categorySetting = "popular";
    private String sortSetting;
    private int pagesPerLoadingSetting = 1;
    private int movieRating = 5;
    private String releaseYear = "";

    public int getMovieRating() {
        return movieRating;
    }

    public Settings setMovieRating(int movieRating) {
        this.movieRating = movieRating;
        return this;
    }

    public String getReleaseYear() {
        return releaseYear;
    }

    public Settings setReleaseYear(String releaseYear) {
        this.releaseYear = releaseYear;
        return this;
    }

    public Settings setCategorySetting(String categorySetting) {
        this.categorySetting = categorySetting;
        return this;
    }

    public Settings setSortSetting(String sortSetting) {
        this.sortSetting = sortSetting;
        return this;
    }

    public Settings setPagesPerLoadingSetting(int pagesPerLoadingSetting) {
        this.pagesPerLoadingSetting = pagesPerLoadingSetting;
        return this;
    }

    public String getCategorySetting() {
        return categorySetting;
    }

    public String getSortSetting() {
        return sortSetting;
    }

    public int getPagesPerLoadingSetting() {
        return pagesPerLoadingSetting;
    }

    public Settings build() {
        return this;
    }

    @Override
    public String toString() {
        return "SettingsBuilder{" +
                "movieCategoryFilter='" + categorySetting + '\'' +
                ", sortOption='" + sortSetting + '\'' +
                ", pagesPerLoading=" + pagesPerLoadingSetting +
                ", movieRating=" + movieRating +
                ", releaseYear='" + releaseYear + '\'' +
                '}';
    }

}
