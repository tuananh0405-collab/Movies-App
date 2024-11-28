package com.example.anhvt86_3.presentation.fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.EditTextPreference;
import androidx.preference.ListPreference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SeekBarPreference;

import com.example.anhvt86_3.R;
import com.example.anhvt86_3.app.di.MyApplication;
import com.example.anhvt86_3.domain.model.Settings;
import com.example.anhvt86_3.presentation.viewmodel.MovieViewModel;

import javax.inject.Inject;


public class SettingsFragment extends PreferenceFragmentCompat {

@Inject
MovieViewModel viewModel;
    @Override
    public void onCreatePreferences(@Nullable Bundle savedInstanceState, @Nullable String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);
        ((MyApplication) requireContext().getApplicationContext()).appComponent.inject(this);

        // Filter by movie category
        ListPreference filterCategoryPreference = findPreference("filter_movie_category");
        if (filterCategoryPreference != null) {
            filterCategoryPreference.setOnPreferenceChangeListener((preference, newValue) -> {
                String filterValue = (String) newValue;
                updateSettings(filterValue, null, null, null, null);
                return true;
            });
        }

        // Sort by option
        ListPreference sortOptionPreference = findPreference("sort_option");
        if (sortOptionPreference != null) {
            sortOptionPreference.setOnPreferenceChangeListener((preference, newValue) -> {
                String sortValue = (String) newValue;
                updateSettings(null, sortValue, null, null, null);
                return true;
            });
        }

        // Number of pages per loading
        EditTextPreference pagesPerLoadingPreference = findPreference("pages_per_loading");
        if (pagesPerLoadingPreference != null) {
            pagesPerLoadingPreference.setOnPreferenceChangeListener((preference, newValue) -> {
                String pagesValue = (String) newValue;
                int pagesPerLoading = Integer.parseInt(pagesValue);
                updateSettings(null, null, pagesPerLoading, null, null);
                return true;
            });
            // Hiển thị giá trị hiện tại trong phần summary
            pagesPerLoadingPreference.setSummaryProvider(preference -> {
                String value = ((EditTextPreference) preference).getText();
                return value != null ?   value +"":"Not set";
            });

        }

        // Movie Rating
        SeekBarPreference movieRatingPreference = findPreference("movie_rating");
        if (movieRatingPreference != null) {
            movieRatingPreference.setOnPreferenceChangeListener((preference, newValue) -> {
                int ratingValue = (Integer) newValue;
                updateSettings(null, null, null, ratingValue, null);
                return true;
            });
            movieRatingPreference.setSummaryProvider(preference -> {
                int rating = ((SeekBarPreference) preference).getValue();
                return rating + "/10";  // Show rating as 0-10 scale
            });
        }

        // Release Year
        EditTextPreference releaseYearPreference = findPreference("release_year");
        if (releaseYearPreference != null) {
            releaseYearPreference.setOnPreferenceChangeListener((preference, newValue) -> {
                String releaseYearValue = (String) newValue;
                updateSettings(null, null, null, null, releaseYearValue);
                return true;
            });
            releaseYearPreference.setSummaryProvider(preference -> {
                String value = ((EditTextPreference) preference).getText();
                return value != null && !value.isEmpty() ? value : "Not set";
            });
        }
    }

    private void updateSettings(@Nullable String filterCategory, @Nullable String sortOption, @Nullable Integer pagesPerLoading,
                                @Nullable Integer movieRating, @Nullable String releaseYear) {
        // Retrieve current settings
        Settings currentSettings = viewModel.getSettings().getValue();

        // Update settings only if the value is provided (non-null)
        if (currentSettings != null) {
            if (filterCategory != null) {
                currentSettings.setCategorySetting(filterCategory);
            }
            if (sortOption != null) {
                currentSettings.setSortSetting(sortOption);
            }
            if (pagesPerLoading != null) {
                currentSettings.setPagesPerLoadingSetting(pagesPerLoading);
            }
            if (movieRating != null) {
                currentSettings.setMovieRating(movieRating);
            }
            if (releaseYear != null) {
                currentSettings.setReleaseYear(releaseYear);
            }

            // Apply updated settings
            viewModel.updateSettings(
                    currentSettings.getCategorySetting(),
                    currentSettings.getSortSetting(),
                    currentSettings.getPagesPerLoadingSetting(),
                    currentSettings.getMovieRating(),
                    currentSettings.getReleaseYear()
            );
        }
    }
}