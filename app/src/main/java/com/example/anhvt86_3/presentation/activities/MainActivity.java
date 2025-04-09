package com.example.anhvt86_3.presentation.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.viewpager2.widget.ViewPager2;
import androidx.work.WorkManager;

import com.example.anhvt86_3.R;
import com.example.anhvt86_3.app.di.MyApplication;
import com.example.anhvt86_3.databinding.ActivityMainBinding;
import com.example.anhvt86_3.databinding.NavHeaderBinding;
import com.example.anhvt86_3.domain.model.Reminder;
import com.example.anhvt86_3.domain.model.UserProfile;
import com.example.anhvt86_3.presentation.adapters.ReminderAdapter;
import com.example.anhvt86_3.presentation.adapters.ViewPagerAdapter;
import com.example.anhvt86_3.presentation.viewmodel.MovieViewModel;
import com.example.anhvt86_3.presentation.viewmodel.ProfileViewModel;
import com.example.anhvt86_3.presentation.viewmodel.ReminderViewModel;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity implements Observer<NavController> {
    private ActivityMainBinding binding;
    private NavController currentNavController;
    @Inject
    MovieViewModel movieViewModel;
    @Inject
    ProfileViewModel profileViewModel;
    @Inject
    ReminderViewModel reminderViewModel;
    ReminderAdapter reminderAdapter;

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
            badgeDrawable.setVisible(false);
        }
        movieViewModel.getFavoriteCountLiveData().observe(this, count -> {
            if (count > 0) {
                if (favoriteTab != null) {
                    BadgeDrawable badge = favoriteTab.getOrCreateBadge();
                    if (count >= 0) {
                        badge.setVisible(true);
                        badge.setNumber(count);
                    } else {
                        badge.setVisible(false);
                    }
                }
            }
        });
        // Tab size
        for (int i = 0; i < binding.tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = binding.tabLayout.getTabAt(i);
            if (tab != null && tab.view != null) {
                tab.view.setLayoutParams(new LinearLayout.LayoutParams(
                        0,
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        1.0f
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
        String category = sharedPreferences.getString("filter_movie_category", "popular");
        String sort = sharedPreferences.getString("sort_option", "rating");
        String pagesPerLoading = sharedPreferences.getString("pages_per_loading", "1");
        int movieRating = sharedPreferences.getInt("movie_rating", 5);
        String releaseYear = sharedPreferences.getString("release_year", "2024");
        movieViewModel.updateSettings(category, sort, Integer.parseInt(pagesPerLoading), movieRating, releaseYear);

        NavHeaderBinding navHeaderBinding = NavHeaderBinding.bind(
                binding.navigationView.getHeaderView(0)
        );

        navHeaderBinding.btnEditProfile.setOnClickListener(view -> {
            currentNavController.navigate(R.id.profileFragment);
            movieViewModel.setProfileReminderFragment(R.id.profileFragment);

            drawerLayout.close();
        });
        navHeaderBinding.btnShowReminders.setOnClickListener(view -> {
            currentNavController.navigate(R.id.reminderFragment);
            movieViewModel.setProfileReminderFragment(R.id.reminderFragment);
            drawerLayout.close();
        });
        movieViewModel.getProfileReminderFragment().observe(this, id -> {
            if (id != -1) {
                // Disable swipe and hide TabLayout
                binding.viewPager.setUserInputEnabled(false); // Disable swipe navigation
                binding.tabLayout.setVisibility(View.GONE); // Hide TabLayout
            } else {
                binding.viewPager.setUserInputEnabled(true); // Disable swipe navigation
                binding.tabLayout.setVisibility(View.VISIBLE); // Hide TabLayout
            }
        });
        profileViewModel.getUserProfileLiveData().observe(this, userProfile -> {
            if (userProfile != null) {
                navHeaderBinding.setProfile(userProfile);
            } else {
                navHeaderBinding.setProfile(new UserProfile("null", "null", "null", "null", "unknown"));
            }
        });
        reminderAdapter = new ReminderAdapter(new ArrayList<>(), reminderViewModel, movieViewModel, true);
        reminderAdapter.setLifecycleOwner(this);
        navHeaderBinding.rcvReminder.setLayoutManager(new LinearLayoutManager(this));
        navHeaderBinding.rcvReminder.setAdapter(reminderAdapter);
        reminderViewModel.getAllReminders().observe(this, this::updateReminders);

        IntentFilter filter = new IntentFilter("UPDATE_REMINDERS");
        registerReceiver(remindersUpdateReceiver, filter, Context.RECEIVER_NOT_EXPORTED);

        handleIntent(getIntent());
    }

    private final BroadcastReceiver remindersUpdateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int movieId = intent.getIntExtra("movieId", -1);
            if (movieId != -1) {
                reminderViewModel.deleteReminderByMovieId(movieId);
                reminderViewModel.updateReminderInfo(movieId, "");
                movieViewModel.setNotifyMovieId(movieId);

                WorkManager.getInstance(context).cancelAllWorkByTag(String.valueOf(movieId));
                Log.e("Uwork tag", WorkManager.getInstance(context).getWorkInfosByTag(String.valueOf(movieId)).toString());
            }

        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(remindersUpdateReceiver);
    }

    private void updateReminders(List<Reminder> reminders) {
        if (reminders != null) {
            reminderAdapter.setRemindersNavHeader(reminders);
        }
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
        movieViewModel.setProfileReminderFragment(-1);

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

    @Override
    protected void onNewIntent(@NonNull Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        Log.e("MainActivity", "onNewIntent called");

        // Check if the intent has the 'notificationId' extra
        if (intent.hasExtra("notificationId")) {
            int notificationId = intent.getIntExtra("notificationId", -1);  // Get notificationId from the intent
            Log.d("MainActivity", "Notification clicked! notificationId: " + notificationId);
            movieViewModel.setNotifyMovieId(notificationId);

        } else {
            Log.e("MainActivity", "No notificationId found in the intent.");
        }
    }

}