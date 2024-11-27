package com.example.anhvt86_3.presentation.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.navigation.NavController;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.viewpager2.widget.ViewPager2;

import com.example.anhvt86_3.R;
import com.example.anhvt86_3.app.di.MyApplication;
import com.example.anhvt86_3.databinding.ActivityMainBinding;
import com.example.anhvt86_3.presentation.adapters.ViewPagerAdapter;
import com.example.anhvt86_3.presentation.viewmodel.MovieViewModel;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity implements Observer<NavController> {
    private ActivityMainBinding binding;
    private NavController currentNavController;
    @Inject
    MovieViewModel movieViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ((MyApplication) getApplicationContext()).appComponent.inject(this);

        setSupportActionBar(binding.toolbar);

        // Set up view pager and tab layout
        ViewPagerAdapter adapter = new ViewPagerAdapter(this);
        binding.viewPager.setAdapter(adapter);
        new TabLayoutMediator(binding.tabLayout, binding.viewPager, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setIcon(R.drawable.ic_home);
                    break;
                case 1:
                    tab.setIcon(R.drawable.ic_favorite);
                    break;
                case 2:
                    tab.setIcon(R.drawable.ic_settings);
                    break;
                case 3:
                    tab.setIcon(R.drawable.ic_about);
                    break;
            }
        }).attach();

        // Switch tab event
        binding.viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                setAppBarNavigation(adapter, position);
            }
        });

        // Initialize badges
        TabLayout.Tab favoriteTab = binding.tabLayout.getTabAt(1);
        if (favoriteTab != null) {
            BadgeDrawable badgeDrawable = binding.tabLayout.getTabAt(1).getOrCreateBadge();
            badgeDrawable.setVisible(false); // Initially hidden
        }

        // Tab size
        for (int i = 0; i < binding.tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = binding.tabLayout.getTabAt(i);
            if (tab != null && tab.view != null) {
                tab.view.setLayoutParams(new LinearLayout.LayoutParams(
                        0,
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        1.0f // This makes sure each tab takes equal width
                ));
            }
        }

        // Initialize drawer toggle
        DrawerLayout drawerLayout = binding.drawerLayout;
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
        );
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        SharedPreferences sharedPreferences = getSharedPreferences("com.example.anhvt86_3_preferences", MODE_PRIVATE);
        String category = sharedPreferences.getString("filter_movie_category", "popular"); // Giá trị mặc định là "popular"
        String sort = sharedPreferences.getString("sort_option", "rating"); // Giá trị mặc định là "desc"
        String pagesPerLoading = sharedPreferences.getString("pages_per_loading", "1"); // Giá trị mặc định là 10
        int movieRating = sharedPreferences.getInt("movie_rating", 5); // Giá trị mặc định là 5
        String releaseYear = sharedPreferences.getString("release_year", "2024"); // Giá trị mặc định là rỗng
        movieViewModel.updateSettings(category, sort, Integer.parseInt(pagesPerLoading), movieRating, releaseYear);
    }

    private void setAppBarNavigation(ViewPagerAdapter adapter, int position) {
        try {
            for (MutableLiveData<NavController> navControllerLiveData : adapter.getNavControllerMap().values()) {
                navControllerLiveData.removeObserver(MainActivity.this);
            }
            adapter.getNavControllerMap().get(position).observe(this, MainActivity.this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onChanged(NavController navController) {
        currentNavController = navController;
        setAppBarNavigation();
    }

    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(currentNavController, binding.drawerLayout);
    }

    private void setAppBarNavigation() {
        if (currentNavController != null) {
            AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(currentNavController.getGraph())
                    .setOpenableLayout(binding.drawerLayout)
                    .build();

            NavigationUI.setupActionBarWithNavController(MainActivity.this, currentNavController, appBarConfiguration);
            NavigationUI.setupWithNavController(binding.navigationView, currentNavController);
        }
    }

}