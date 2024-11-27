package com.example.anhvt86_3.domain.model;

public class Settings {
    private String categorySetting = "popular";
    private String sortSetting;
    private int pagesPerLoadingSetting = 1;

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
                '}';
    }

}
